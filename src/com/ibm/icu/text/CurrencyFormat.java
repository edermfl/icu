/*
**********************************************************************
* Copyright (c) 2004, International Business Machines
* Corporation and others.  All Rights Reserved.
**********************************************************************
* Author: Alan Liu
* Created: April 20, 2004
* Since: ICU 3.0
**********************************************************************
*/
package com.ibm.icu.text;

import java.text.FieldPosition;
import java.text.ParsePosition;

import com.ibm.icu.util.CurrencyAmount;
import com.ibm.icu.util.ULocale;

/**
 * Temporary internal concrete subclass of MeasureFormat implementing
 * parsing and formatting of CurrencyAmount objects.  This class is
 * likely to be redesigned and rewritten in the near future.
 *
 * <p>This class currently delegates to DecimalFormat for parsing and
 * formatting.
 *
 * @see com.ibm.icu.text.UFormat
 * @see com.ibm.icu.text.DecimalFormat
 * @author Alan Liu
 * @internal
 */
class CurrencyFormat extends MeasureFormat {

    private NumberFormat fmt;

    public CurrencyFormat(ULocale locale) {
        fmt = NumberFormat.getCurrencyInstance(locale.toLocale());
    }

	/**
     * Override Format.format().
	 * @see java.text.Format#format(java.lang.Object, java.lang.StringBuffer, java.text.FieldPosition)
	 */
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		try {
            CurrencyAmount currency = (CurrencyAmount) obj;
            fmt.setCurrency(currency.getCurrency());
            return fmt.format(currency.getNumber(), toAppendTo, pos);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Invalid type: " + obj.getClass().getName());
        }
	}

	/**
     * Override Format.parseObject().
	 * @see java.text.Format#parseObject(java.lang.String, java.text.ParsePosition)
	 */
	public Object parseObject(String source, ParsePosition pos) {
        return fmt.parseCurrency(source, pos);
	}
}
