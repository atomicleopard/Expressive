/*
 *  Copyright (c) 2011 Nicholas Okunew
 *  All rights reserved.
 *  
 *  This file is part of the com.atomicleopard.expressive library
 *  
 *  The com.atomicleopard.expressive library is free software: you 
 *  can redistribute it and/or modify it under the terms of the GNU
 *  Lesser General Public License as published by the Free Software Foundation, 
 *  either version 3 of the License, or (at your option) any later version.
 *  
 *  The com.atomicleopard.expressive library is distributed in the hope
 *  that it will be useful, but WITHOUT ANY WARRANTY; without even
 *  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with the com.atomicleopard.expressive library.  If not, see 
 *  http://www.gnu.org/licenses/lgpl-3.0.html.
 */
package com.atomicleopard.selector;

public class PojoWithAllBasicTypes {
	private byte bbyte;
	private char cchar;
	private int iint;
	private long llong;
	private short sshort;
	private double ddouble;
	private float ffloat;
	private boolean bboolean;

	public byte getBbyte() {
		return bbyte;
	}

	public void setBbyte(byte bbyte) {
		this.bbyte = bbyte;
	}

	public char getCchar() {
		return cchar;
	}

	public void setCchar(char cchar) {
		this.cchar = cchar;
	}

	public int getIint() {
		return iint;
	}

	public void setIint(int iint) {
		this.iint = iint;
	}

	public long getLlong() {
		return llong;
	}

	public void setLlong(long llong) {
		this.llong = llong;
	}

	public short getSshort() {
		return sshort;
	}

	public void setSshort(short sshort) {
		this.sshort = sshort;
	}

	public double getDdouble() {
		return ddouble;
	}

	public void setDdouble(double ddouble) {
		this.ddouble = ddouble;
	}

	public float getFfloat() {
		return ffloat;
	}

	public void setFfloat(float ffloat) {
		this.ffloat = ffloat;
	}

	public boolean isBboolean() {
		return bboolean;
	}

	public void setBboolean(boolean bboolean) {
		this.bboolean = bboolean;
	}
}
