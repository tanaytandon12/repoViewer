package com.tandon.tanay.githubrepoviewer.util;


import java.util.regex.Pattern;

public enum TextUtils {

    INSTANCE;

    public boolean isValidInput(String id) {
        Pattern pattern = Pattern.compile("([A-Za-z])+([0-9])*");
        return pattern.matcher(id).matches();
    }
}
