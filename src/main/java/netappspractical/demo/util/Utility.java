package netappspractical.demo.util;

import netappspractical.demo.domain.Estate;

import java.util.function.Predicate;

public class Utility {
    /**
     * Streams.
     */
    public static Predicate<Estate> unsoldEstates = new Predicate<Estate>() {
        @Override
        public boolean test(Estate estate) {
            return !estate.getSold();
        }
    };

    public static Predicate<Estate> query(String query) {
        return new Predicate<Estate>() {
            @Override
            public boolean test(Estate estate) {
                return query != null ?
                        estate.getName().toUpperCase().contains(query.toUpperCase()): true;
            }
        };
    }
}
