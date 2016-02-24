/**
 * # gunziper for Burpsuite
 * #
 * # Copyright (c) 2012, Frank Block <gunziper@f-block.org>
 * #
 * # All rights reserved.
 * #
 * # Redistribution and use in source and binary forms, with or without
 * modification,
 * # are permitted provided that the following conditions are met:
 * #
 * # * Redistributions of source code must retain the above copyright notice,
 * this
 * # list of conditions and the following disclaimer.
 * # * Redistributions in binary form must reproduce the above copyright notice,
 * # this list of conditions and the following disclaimer in the documentation
 * # and/or other materials provided with the distribution.
 * # * The names of the contributors may not be used to endorse or promote
 * products
 * # derived from this software without specific prior written permission.
 * #
 * # THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * # AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * # IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * # ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * # LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL
 * # DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * # SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * # CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY,
 * # OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE
 * # OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package misc;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class Messages {

    private static String         bundleName     = "misc.messages_en";                   //$NON-NLS-1$
    private static ResourceBundle resourceBundle = ResourceBundle
                                                         .getBundle(Messages.bundleName);

    public static String getString(String key) {

        try {
            return Messages.resourceBundle.getString(key);
        }catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }


    public static void setLanguage(String lang) {

        try {
            Messages.bundleName = Messages.bundleName.replaceAll("_..", "_"
                    + lang);
        }catch (MissingResourceException e) {
            Messages.bundleName = "misc.messages_en";
        }finally {
            Messages.resourceBundle = ResourceBundle
                    .getBundle(Messages.bundleName);
        }
    }

    private Messages() {

    }
}
