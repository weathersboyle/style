package com.intrepid.style;

import com.google.auto.service.AutoService;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class StyleProcessor extends AbstractProcessor {
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportedTypes = new LinkedHashSet<>();
        supportedTypes.add(Style.class.getCanonicalName());
        return supportedTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            Map<TypeElement, StyleableViewClass> styleablesMap = createStyleableViewClasses(roundEnv);
            //todo: write files
        } catch (ProcessingException e) {
            onError(e.getElement(), e.getMessage());
        }
        return true;
    }

    private Map<TypeElement, StyleableViewClass> createStyleableViewClasses(RoundEnvironment roundEnv)
            throws ProcessingException {
        Map<TypeElement, StyleableViewClass> styleablesMap = new LinkedHashMap<>();
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Style.class)) {
            StyleValidator.validateAnnotatedElement(annotatedElement);
            addAttributeToViewClass(annotatedElement, styleablesMap);
        }
        return styleablesMap;
    }

    private void addAttributeToViewClass(Element element, Map<TypeElement, StyleableViewClass> styleablesMap) {
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        StyleableViewClass viewClass = styleablesMap.get(enclosingElement);
        if (viewClass == null) {
            styleablesMap.put(enclosingElement, (viewClass = new StyleableViewClass(
                    enclosingElement.getSimpleName().toString())));
        }
        viewClass.addAttribute(createStyleAttribute(element));
    }

    private StyleAttribute createStyleAttribute(Element element) {
        String name = element.getSimpleName().toString();
        TypeKind type = TypeUtils.getTypeKind(element);
        Style style = element.getAnnotation(Style.class);
        return new StyleAttribute(name, type, getAttrType(style), getDefaultValueString(style, type));
    }

    private AttributeType getAttrType(Style style) {
        return style.attrType();
    }

    private String getDefaultValueString(Style style, TypeKind type) {
        String value = null;
        /**
         * default value only relevant when obtaining value for attribute that has one of the following primitive types
         */
        switch (type) {
            case INT:
                value = String.valueOf(style.defValueInt());
                break;
            case FLOAT:
                value = String.valueOf(style.defValueFloat());
                break;
            case BOOLEAN:
                value = String.valueOf(style.defValueBool());
                break;
        }
        return value;
    }

    private void onError(Element element, String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
