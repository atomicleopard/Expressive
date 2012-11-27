/**
 * <p>
 * <b>{@link com.atomicleopard.expressive.Expressive} is a java library designed to enable simple and easily read usage
 * of the Java Collections API.</b>
 * </p>
 * <p>
 * Java provides a collections API which is effective and powerful,
 * but can be cumbersome for simple usages. 
 * This can often result in a lack of clarity of the intention of code.
 * </p>
 * <p>
 * Expressive provides methods to allow for simple but powerful interactions with java collections so that
 * the focus of your code can be its function, rather than the mechanics of java collections.
 * </p>
 * <p>
 * As an example, consider a method which takes a collection as a parameter, for example a list.
 * While in all likelihood your application code will only invoke the method from a few places,
 * you will more than likely be invoking the same method tens of times from unit tests.
 * </p>
 * <p>
 * In this situation the collections you wish to pass in are well defined and understood. As
 * such a creating collections in a way that is easily readable has more value
 * than a more formal collection creation strategy.
 * </p>
 * 
 * <h2>Expressive.list(Object...)</h2>
 * 
 * <p>List Example - the stock standard way:
 * 
 * <pre>
 * public void standardLookingTest() {
 * 	PhoneNumber ph1 = new PhoneNumber(&quot;1234-5555&quot;);
 * 	PhoneNumber ph2 = new PhoneNumber(&quot;1234-6666&quot;);
 * 	PhoneNumber ph3 = new PhoneNumber(&quot;1234-7777&quot;);
 * 
 * 	List&lt;PhoneNumber&gt; phoneNumbers = new ArrayList&lt;PhoneNumber&gt;();
 * 	phoneNumbers.add(ph1);
 * 	phoneNumbers.add(ph2);
 * 	phoneNumbers.add(ph3);
 * 
 * 	String result = concatenatePhoneNumbers(phoneNumbers);
 * }
 * </pre>
 * </p>
 * <p>
 * List example - the {@link com.atomicleopard.expressive.Expressive#list(Object...)} method:
 * 
 * <pre>
 * public void succinctTest() {
 * 	PhoneNumber ph1 = new PhoneNumber(&quot;1234-5555&quot;);
 * 	PhoneNumber ph2 = new PhoneNumber(&quot;1234-6666&quot;);
 * 	PhoneNumber ph3 = new PhoneNumber(&quot;1234-7777&quot;);
 * 
 * 	List&lt;PhoneNumber&gt; phoneNumbers = list(ph1, ph2, ph3);
 * 
 * 	String result = concatenatePhoneNumbers(phoneNumbers);
 * }
 * </pre>
 * </p>
 * 
 * <h2>Expressive.map(Object...)</h2> 
 * <p>While convenience methods
 * for lists are useful, the above usage would have been equally met by
 * Arrays.asList . In this example, we will see the way Expressive can create
 * maps, which has no analogy. In particular, maps are useful for static
 * reference data and lookups, but creation of these lookups is usually
 * cumbersome.
 * </p>
 * <p>
 * Reference map creation - the standard way:
 * <pre >
 * public static Map&lt;String, String&gt; testMapData;
 * @BeforeClass
 * public static void beforeClass(){
 * 	testMapData = new HashMap&gt;String, String&lt;();
 * 	testMapData.put("Mr", "Male");
 * 	testMapData.put("Mrs", "Female");
 * 	testMapData.put("Dr", "Both");
 * }
 * </pre>
 * </p>
 * <p>
 * The {@link com.atomicleopard.expressive.Expressive#map(Object...)} or {@link com.atomicleopard.expressive.Expressive#mapKeys(Object...)} methods.
 * 
 * <pre >
 * public static Map&lt;String, String&gt; testMapData2 = map(&quot;Mr&quot;, &quot;Male&quot;, &quot;Mrs&quot;, &quot;Female&quot;, &quot;Dr&quot;, &quot;Both&quot;);
 * public static Map&lt;String, String&gt; testMapTypeSafe = mapKeys(&quot;Mr&quot;, &quot;Mrs&quot;, &quot;Dr&quot;).to(&quot;Male&quot;, &quot;Female&quot;, &quot;Both&quot;);
 * </pre>
 * </p>
 * 
 * <h2>{@link com.atomicleopard.expressive.ETransformer}</h2> <p>
 * It is often the case that we deal with collections and
 * loops in order to perform a data transformation. Java syntax for iterating
 * over collections does not intrinsically support parallel iteration or lambda
 * expressions (closures). This means that transformations of this type are
 * usually verbose, can clutter scope with unnecessary variables, and are
 * inconsistent in that they do not follow a standard pattern.
 * </p>
 * <p>
 * <b>The ETransformer interface is provided to help add consistency and tooling
 * around object transformations.</b>
 * </p>
 * <p>
 * By defining a class that can transform between two data representations, code
 * that requires transformations can clearly and easily leverage this behavior
 * in a way that allows the intention to be clear and uncluttered. It also
 * encourages thorough and independent testability of the transformation code as
 * well as the utilizing code. This benefits the developer at build time because
 * it offers concise and testable code, the development team because it offers
 * clarity, modularity and reusability, and maintenance developers because
 * transformation code is in one place, well tested and the intent of calling
 * code is clear.
 * </p>
 * <p>
 * Standard data transformation:
 * <pre >
 * public boolean standardLookingTransformationCode(String accountNumber, List&lt;PhoneNumber&gt; phoneNumbers) {
 * 	List&lt;ContactDetail&gt; contactDetails = new ArrayList&lt;ContactDetail&gt;(phoneNumbers.size());
 * 	for (PhoneNumber phoneNumber : phoneNumbers) {
 * 		ContactDetail contactDetail = new ContactDetail();
 * 		contactDetail.setPhoneNumber(phoneNumber.getNumber());
 * 		contactDetails.add(contactDetail);
 * 	}
 * 	AccountInformation accountInfo = new AccountInformation();
 * 	accountInfo.setAccountId(accountNumber);
 * 	accountInfo.setContactDetails(contactDetails);
 * 
 * 	return serviceMethod(accountInfo);
 * }
 * </pre>
 * </p>
 * <p>
 * Basic ETransformer usage:
 * 
 * <pre >
 * public static class PhoneNumberToContactDetailsTransformer implements ETransformer&lt;PhoneNumber, ContactDetail&gt; {
 * 	public ContactDetail to(PhoneNumber from) {
 * 		ContactDetail contactDetail = new ContactDetail();
 * 		contactDetail.setPhoneNumber(from.getNumber());
 * 		return contactDetail;
 * 	}
 * }
 * 
 * private static final ETransformer&lt;PhoneNumber, ContactDetail&gt; transformer = new PhoneNumberToContactDetailsTransformer();
 * 
 * public boolean expressiveTransformationCode(String accountNumber, List&lt;PhoneNumber&gt; phoneNumbers) {
 * 	List&lt;ContactDetail&gt; contactDetails = new ArrayList&lt;ContactDetail&gt;(phoneNumbers.size());
 * 	for (PhoneNumber phoneNumber : phoneNumbers) {
 * 		contactDetails.add(transformer.to(phoneNumber));
 * 	}
 * 	AccountInformation accountInfo = new AccountInformation();
 * 	accountInfo.setAccountId(accountNumber);
 * 	accountInfo.setContactDetails(contactDetails);
 * 
 * 	return serviceMethod(accountInfo);
 * }
 * </pre>
 *</p>
 *<p> 
 * While the above sample as it stands does not cut down on code, it does
 * introduce a separation of concerns. Next we'll see how to leverage the
 * CollectionTransformer to make the same code more succinct.
 * </p>
 * <h2>{@link com.atomicleopard.expressive.Expressive.Transformers}</h2>
 * After using the patterns and tools provided by the Expressive library for months, it became apparent that the majority of use cases revolving around transformations are satisfied by a very small set of operations.
 * Expressive.Transformers provides static factory methods for creating the most common types of ETransformer. These are:
 * <ul>
 * <li>Extracting javabean properies - such as getting the postcode from an address object - provided by ETransformer.toProperty()</li>
 * <li>Doing a Map lookup - for example transforming from one enum to an equivalent enum - provided by ETransformers.usingLookup()</li>
 * <li>Creating a lookup map based on a javabean property - for example creating a map of PK to entity from a list of JPA Entities - provided by ETransformers.toKeyBeanLookup()</li>
 * <li>Creating a lookup map based on a javabean property where the key is not unique- for example creating a map of postcode to address objects - provided by ETransformers.toBeanLookup()</li>
 * <li>Transforming a collection using a specified transformer - as below - ETransformers.transformAllUsing()</li>
 * </ul>
 * <h2>{@link com.atomicleopard.expressive.transform.CollectionTransformer}</h2> 
 * <p>Once a structured pattern exists for transformations, we can leverage this to succinctly perform transformations
 * on groups and structures of data objects.
 * 
 * Using the ETransformer example above and the ETransformers.transformAllUsing():
 * <pre>
 * private static CollectionTransformer&lt;PhoneNumber, ContactDetail&gt; collectionTransformer =  ETransformers.transformAllUsing(new PhoneNumberToContactDetailsTransformer());
 *
 * public boolean expressiveTransformationCode(String accountNumber, List&lt;PhoneNumber&gt; phoneNumbers) {
 *	List&lt;ContactDetail&gt; contactDetails = collectionTransformer.to(phoneNumbers);
 *	AccountInformation accountInfo = new AccountInformation();
 *	accountInfo.setAccountId(accountNumber);
 *	accountInfo.setContactDetails(contactDetails);
 *
 *	return serviceMethod(accountInfo);
 * }
 * </pre>
 * </p>
 * <p>
 * In additional to the {@link com.atomicleopard.expressive.transform.CollectionTransformer}, there is an {@link com.atomicleopard.expressive.IteratorTransformer}.
 * <pre>
 * 		Iterator&lt;Date&gt; dateIterator;
 *		ETransformer&lt;Date, String&gt; transformer;
 *		Iterator&lt;String&gt; stringIterator = new IteratorTransformer&lt;Date, String&gt;(transformer, dateIterator);
 * </pre>
 * </p>
 * 
 * <h2>The {@link com.atomicleopard.expressive.EList}</h2> <p>As well as convenience methods for constructing java
 * collections, expressive defines a new type of list, the EList. The EList
 * conforms to the java collections API, but is designed to support common
 * operations first class. These operations are 'fail safe' - that is where
 * using standard List methods would throw an exception, EList will return null.
 * This often allows easier and better handling of special cases, and helps us
 * avoid superfluous try/catch blocks. In the cases where EList returns null
 * rather than throwing an exception it is clearly documented, and EList always
 * conforms to the List interface, throwing exceptions as required on standard
 * methods.
 * </p>
 * <p>
 * EList convenience operations:
 * 
 * <pre >
 * public List&lt;String&gt; usageOfElist() {
 * 	EList&lt;String&gt; list = list(&quot;a&quot;, &quot;b&quot;, &quot;c&quot;);
 * 	String firstEntry = list.first();
 * 	String lastEntry = list.last();
 * 	String item1 = list.at(1);
 * 	return list.duplicate();
 * }
 * </pre>
 * </p>
 * <p>
 * ELists also support a fluid syntax so that operations can be chained,
 * allowing rapid and succinct creation of lists. This is particularly useful
 * when constructing collections under controlled circumstances, such as unit
 * tests and reference data collections.
 * </p>
 * <p>
 * Example of EList method chaining:
 * 
 * <pre >
 * public Controller() {
 * 	referenceTitlesMale = list(&quot;Mr&quot;);
 * 	referenceTitlesFemale = list(&quot;Mrs&quot;, &quot;Ms&quot;);
 * 	referenceTitlesBoth = list(&quot;Dr&quot;);
 * 	referenceTitlesAll = list(referenceDataMaleTitles).addItems(referenceTitlesFemale).addItems(referenceTitlesBoth);
 * 	// our system only handle non-ambiguous titles
 * 	referenceValidTitles = list(referenceDataTitlesAll).removeItems(referenceTitlesBoth);
 * }
 * </pre>
 * </p>
 * 
 * <h2>Cast.as(Object, Class)</h2> <p>Cast defines a casting operation which functions the same
 * as the .net 'as' keyword, that is it performs a cast or returns a null if no
 * cast can be made. This is an alternative to the java cast operator, which
 * throws a ClassCastException. Once again, this allows more succinct code to
 * handle expected edge cases than a try/catch block which treats failure to
 * cast as exceptional.
 * 
 * <pre >
 * public String findAnItemFromTheIterable(int index, Iterable&lt;String&gt; iterable) {
 * 	List&lt;String&gt; list = Cast.as(iterable, List.class);
 * 	if (list != null) { // if this is a list, do a quick direct lookup
 * 		return list.get(index);
 * 	}
 * 	// if this is an iterator, iterate until the specified item
 * 	Iterator&lt;String&gt; iterator = iterable.iterator();
 * 	for (int i = 0; i &lt; index; i++) {
 * 		iterator.next();
 * 	}
 * 	return iterator.next();
 * }
 * </pre>
 * </p>
 * 
 * <h2>Expressive.iterable()</h2><p> There are some java framework classes,
 * particularly older ones which provide Enumeration and Iterators which are not
 * compatible which the java for each syntax. Expressive defines methods for
 * enabling both of these to be used as Iterable for convenience.
 * 
 * <pre >
 * public InetAddress getFirstMulticaseInetAddress(NetworkInterface networkInterface) {
 * 	Enumeration&lt;InetAddress&gt; inetAddresses = networkInterface.getInetAddresses();
 * 	for (InetAddress inetAddress : Expressive.iterable(inetAddresses)) {
 * 		if (inetAddress.isMulticastAddress()) {
 * 			return inetAddress;
 * 		}
 * 	}
 * 	return null;
 * }
 * 
 * public boolean supportsColorModel(ImageReader reader, ColorModel colorModel) throws IOException {
 * 	Iterator&lt;ImageTypeSpecifier&gt; imageTypes = reader.getImageTypes(0);
 * 	for (ImageTypeSpecifier imageTypeSpecifier : iterable(imageTypes)) {
 * 		if (colorModel.equals(imageTypeSpecifier.getColorModel())) {
 * 			return true;
 * 		}
 * 	}
 * 	return false;
 * }
 * </pre>
 * </p>
 */
package com.atomicleopard.expressive;

