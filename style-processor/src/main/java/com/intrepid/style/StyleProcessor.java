package com.intrepid.style;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class StyleProcessor extends AbstractProcessor {
    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        LangModelUtils.init(processingEnv.getElementUtils(), processingEnv.getTypeUtils());
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
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
            createSourceDependencies(styleablesMap);
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
            String packageName = LangModelUtils.getPackageName(enclosingElement);
            String qualifiedName = enclosingElement.getQualifiedName().toString();
            String simpleName = enclosingElement.getSimpleName().toString();
            styleablesMap.put(enclosingElement, (viewClass = new StyleableViewClass(packageName, qualifiedName,
                    simpleName)));
        }
        viewClass.addAttribute(createStyleAttribute(element));
    }

    private StyleAttribute createStyleAttribute(Element element) {
        String name = element.getSimpleName().toString();
        Style style = element.getAnnotation(Style.class);
        return new StyleAttribute(name, getAttrType(style), getDefaultValueString(style, element));
    }

    private AttributeType getAttrType(Style style) {
        return style.attrType();
    }

    private String getDefaultValueString(Style style, Element element) {
        String value = null;
        TypeKind kind = LangModelUtils.getTypeKind(element);
        /**
         * default value only relevant when obtaining value for attribute that has one of the following primitive types
         * or the type of their boxed variants, respectively
         */
        switch (kind) {
            case INT:
                value = String.valueOf(style.defValueInt());
                break;
            case FLOAT:
                value = String.valueOf(style.defValueFloat());
                break;
            case BOOLEAN:
                value = String.valueOf(style.defValueBoolean());
                break;
            case DECLARED:
                DeclaredType type = (DeclaredType) element.asType();
                if (LangModelUtils.isBoxedInt(type)) {
                    value = String.valueOf(style.defValueInt());
                } else if (LangModelUtils.isBoxedFloat(type)) {
                    value = String.valueOf(style.defValueFloat());
                } else if (LangModelUtils.isBoxedBoolean(type)) {
                    value = String.valueOf(style.defValueBoolean());
                }
                break;
        }
        return value;
    }

    private void createSourceDependencies(Map<TypeElement, StyleableViewClass> styleablesMap)
            throws ProcessingException {
        for (Map.Entry<TypeElement, StyleableViewClass> entry : styleablesMap.entrySet()) {
            StyleableViewClass viewClass = entry.getValue();
            try {
                viewClass.createSourceFile().writeTo(filer);
            } catch (IOException e) {
                throw new ProcessingException(entry.getKey(), "Failed to generate source file for %s",
                        viewClass.getQualifiedName());
            }
        }
    }

    private void onError(Element element, String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
