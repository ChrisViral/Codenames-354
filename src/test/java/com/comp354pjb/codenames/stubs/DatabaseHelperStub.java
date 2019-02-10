package com.comp354pjb.codenames.stubs;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

public class DatabaseHelperStub {
    private static final String[] store = {"apple", "wheel", "grape", "hoard", "carry", "axolotl", "tenor",
            "guitar", "bee", "bucket", "kite", "table", "pants", "orange", "terrible",
            "open", "jar", "assembly", "pot", "boring", "fall", "light", "box", "smock",
            "crayon", "sculpture", "caramel", "tennis", "umbrella", "gong", "sheep",
            "boil", "scam", "shade", "dubious", "crazy", "salt", "label", "tree", "ball",
            "conscious", "humble", "yellow", "meter", "cable", "fat", "mingle", "rebel",
            "asterisk", "king"};

    public static String[] selectWords(int n) {
        String[] db = getStore();
        Collections.shuffle(Arrays.asList(db));
        String[] words = new String[n];
        for(int i = 0; i < words.length; i++) {
            words[i] = db[i];
        }
        return words;
    }

    public static String[] getStore() {
        String[] words = Arrays.copyOf(store, store.length);
        return words;
    }
}
