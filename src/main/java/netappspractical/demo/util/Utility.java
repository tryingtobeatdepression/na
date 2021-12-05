package netappspractical.demo.util;

import netappspractical.demo.domain.Estate;
import netappspractical.demo.domain.Role;

import java.util.function.Function;
import java.util.function.Predicate;

public class Utility {
    /**
     * Filter out unsold Estates.
     */
    public static Predicate<Estate> unsoldEstates = new Predicate<Estate>() {
        @Override
        public boolean test(Estate estate) {
            return !estate.getSold();
        }
    };

    /**
     * Filter out Estates by query input.
     * @param query
     * @return
     */
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
