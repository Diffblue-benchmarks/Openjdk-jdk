/*
 * Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
import java.util.Locale;

public class LocaleCategory {
    private static Locale base = null;
    private static Locale disp = null;
    private static Locale fmt = null;
    private static String enc = null;

    public static void main(String[] args) {
        Locale reservedLocale = Locale.getDefault();
        try {
            Locale.Builder builder = new Locale.Builder();

            base = builder.setLanguage(System.getProperty("user.language", ""))
                  .setScript(System.getProperty("user.script", ""))
                  .setRegion(System.getProperty("user.country", ""))
                  .setVariant(System.getProperty("user.variant", "")).build();
            disp = builder.setLanguage(
                    System.getProperty("user.language.display",
                                Locale.getDefault().getLanguage()))
                        .setScript(System.getProperty("user.script.display",
                                Locale.getDefault().getScript()))
                        .setRegion(System.getProperty("user.country.display",
                                Locale.getDefault().getCountry()))
                        .setVariant(System.getProperty("user.variant.display",
                                Locale.getDefault().getVariant())).build();
            fmt = builder.setLanguage(System.getProperty("user.language.format",
                                Locale.getDefault().getLanguage()))
                       .setScript(System.getProperty("user.script.format",
                                Locale.getDefault().getScript()))
                       .setRegion(System.getProperty("user.country.format",
                                Locale.getDefault().getCountry()))
                       .setVariant(System.getProperty("user.variant.format",
                                  Locale.getDefault().getVariant())).build();
            checkDefault();
            testGetSetDefault();
        } finally {
            // restore the reserved locale
            Locale.setDefault(reservedLocale);
        }
    }

    static void checkDefault() {
        if (!base.equals(Locale.getDefault()) ||
            !disp.equals(Locale.getDefault(Locale.Category.DISPLAY)) ||
            !fmt.equals(Locale.getDefault(Locale.Category.FORMAT))) {
            throw new RuntimeException("Some of the return values from getDefault() do not agree with the locale derived from \"user.xxxx\" system properties");
        }
    }

    static void testGetSetDefault() {
        try {
            Locale.setDefault(null, null);
            throw new RuntimeException("setDefault(null, null) should throw a NullPointerException");
        } catch (NullPointerException npe) {}

        Locale.setDefault(Locale.CHINA);
        if (!Locale.CHINA.equals(Locale.getDefault(Locale.Category.DISPLAY)) ||
            !Locale.CHINA.equals(Locale.getDefault(Locale.Category.FORMAT))) {
            throw new RuntimeException("setDefault() should set all default locales for all categories");
        }
    }
}

