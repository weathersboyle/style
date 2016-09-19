package style;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.Modifier;

public class StyleableViewClass {
    private static final String STYLEABLE_CLASS_SUFFIX = "$$Styleable";
    private static final String BINDING_METHOD_NAME = "style";
    private static final String VAR_CONTEXT = "context";
    private static final String VAR_ATTR_SET = "attrs";
    private static final String VAR_TARGET = "target";
    private static final String VAR_TYPED_ARRAY = "typedArray";
    private static final ClassName CONTEXT = ClassName.get("android.content", "Context");
    private static final ClassName ATTRIBUTE_SET = ClassName.get("android.util", "AttributeSet");
    private static final ClassName TYPED_ARRAY = ClassName.get("android.content.res", "TypedArray");
    private static final ClassName STYLEABLE_VIEW_BINDER = ClassName.get("style", "StyleableViewBinder");

    private String classPackage;
    private String qualifiedName;
    private String simpleName;
    private Set<StyleAttribute> attributes = new HashSet<>();
    private Author author = new Author();

    public StyleableViewClass(String classPackage, String qualifiedName, String simpleName) {
        this.classPackage = classPackage;
        this.qualifiedName = qualifiedName;
        this.simpleName = simpleName;
    }

    public void addAttribute(StyleAttribute attribute) {
        attributes.add(attribute);
    }

    public JavaFile createSourceFile() {
        return author.authorSourceFile();
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    private class Author {
        private static final String FILE_COMMENT = "Generated code for @Style; please do not modify.";

        public JavaFile authorSourceFile() {
            TypeSpec.Builder result = TypeSpec.classBuilder(getClassName())
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(ParameterizedTypeName.get(STYLEABLE_VIEW_BINDER,
                            ClassName.get(classPackage, simpleName)))
                    .addMethod(createBindStyleAttrsMethod());
            return JavaFile.builder(classPackage, result.build())
                    .addFileComment(FILE_COMMENT)
                    .addStaticImport(ClassName.get("com.intrepid.style", "R"), "*")
                    .build();
        }

        private MethodSpec createBindStyleAttrsMethod() {
            MethodSpec.Builder result = MethodSpec.methodBuilder(BINDING_METHOD_NAME)
                    .addModifiers(Modifier.PUBLIC);
            addBindMethodParams(result);
            result.addStatement("$T $L = $L.$L($L, $L)", TYPED_ARRAY, VAR_TYPED_ARRAY, VAR_CONTEXT,
                    TypedArrayUtils.METHOD_OBTAIN_ATTRS, VAR_ATTR_SET, getStyleableResName(null))
                    .beginControlFlow("try");
            addBindMethodBindings(result);
            result.nextControlFlow("finally")
                    .addStatement("$L.recycle()", VAR_TYPED_ARRAY)
                    .endControlFlow();
            return result.build();
        }

        private void addBindMethodParams(MethodSpec.Builder result) {
            result.addParameter(CONTEXT, VAR_CONTEXT, Modifier.FINAL)
                    .addParameter(ATTRIBUTE_SET, VAR_ATTR_SET, Modifier.FINAL)
                    .addParameter(ClassName.get(classPackage, simpleName), VAR_TARGET, Modifier.FINAL);
        }

        private void addBindMethodBindings(MethodSpec.Builder result) {
            for (StyleAttribute attr : attributes) {
                String typedArrMethod = TypedArrayUtils.getTypedArrMethodName(attr.getAttributeType());
                String typedArrIndex = getStyleableResName(attr);
                String defValue = attr.getDefValue();
                if (defValue != null && defValue.length() > 0) {
                    result.addStatement("$L.$L = $L.$L($L, $L)", VAR_TARGET, attr.getName(), VAR_TYPED_ARRAY,
                            typedArrMethod, typedArrIndex, defValue);
                } else {
                    result.addStatement("$L.$L = $L.$L($L)", VAR_TARGET, attr.getName(), VAR_TYPED_ARRAY,
                            typedArrMethod, typedArrIndex);
                }
            }
        }

        private String getClassName() {
            return simpleName + STYLEABLE_CLASS_SUFFIX;
        }

        private String getStyleableResName(StyleAttribute attr) {
            StringBuilder resName = new StringBuilder();
            resName.append(TypedArrayUtils.STYLEABLE_RES_PREFIX).append(".").append(simpleName);
            if (attr != null) {
                resName.append("_").append(attr.getName());
            }
            return resName.toString();
        }
    }
}
