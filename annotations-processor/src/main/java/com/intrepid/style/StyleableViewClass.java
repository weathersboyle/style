package com.intrepid.style;

import java.util.HashSet;
import java.util.Set;

public class StyleableViewClass {
    private String name;
    private Set<StyleAttribute> attributes = new HashSet<>();

    public StyleableViewClass(String name) {
        this.name = name;
    }

    public void addAttribute(StyleAttribute attribute) {
        attributes.add(attribute);
    }

    public Set<StyleAttribute> getAttributes() {
        return attributes;
    }
}
