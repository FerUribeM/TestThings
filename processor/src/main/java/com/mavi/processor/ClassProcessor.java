package com.mavi.processor;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class ClassProcessor extends AbstractProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment e) {
        //get all elements annotated with AwesomeLogger
        Collection<? extends Element> annotatedElements = e.getElementsAnnotatedWith(Foo.class);

        //filter out elements we don't need
        List<TypeElement> types = new ImmutableList.Builder<TypeElement>()
                .addAll(ElementFilter.typesIn(annotatedElements))
                .build();

        StringBuilder builder = new StringBuilder()
                .append("package\t")
                .append("com.ferbajoo.testthings")
                .append(";\n\n")
                .append("import\t")
                .append("com.ferbajoo.testthings.models.ClassModel")
                .append(";\n")
                .append("import\t")
                .append("com.mavi.processor.Foo")
                .append(";\n\n")
                .append("import\t")
                .append("java.lang.annotation.Annotation")
                .append(";\n")
                .append("import\t")
                .append("java.util.ArrayList")
                .append(";\n/**\n")
                .append("* Auto generate class\n")
                .append("**/\n")
                .append("public final class ")
                .append("GlobalClasses")
                .append("{\n\n")
                //.append("\tpublic static ArrayList<Class> all_classes = new ArrayList<>();")
                //.append("\n\t")
                //.append("static {")
                .append("\tpublic static ArrayList<Class> all_classes = new ArrayList<>();")
                .append("\n\t")
                .append("static {")
                ;
        for (TypeElement type : types) {

            builder.append("\n\t\t")
                    .append("all_classes.add(" + type.asType().toString() + ".class);");
            //builder.append("\n\t//for class" + type.asType().toString());
            //builder.append("\n\tpublic static void LogT(");
            //builder.append(type.asType().toString() + " e){");
            //builder.append("\n\t\tLog.d(\"MAVI PROCESSOR\", e.toString());");
            //builder.append("\n\t}");
        }
        builder.append("\n\t")
                .append("}");

        builder.append("\n\t")
                .append("public static ArrayList<ClassModel> getAllClasses() {")
                .append("\n\t\t")
                .append("ArrayList<ClassModel> models = new ArrayList<>();")
                .append("\n\t\t")
                .append("for (Class aClass : all_classes) {")
                .append("\n\t\t\t")
                .append("for (Annotation annotation : aClass.getDeclaredAnnotations()) {")
                .append("\n\t\t\t\t")
                .append("if (annotation instanceof Foo) {")
                .append("\n\t\t\t\t\t")
                .append("Foo foo = (Foo) annotation;")
                .append("\n\t\t\t\t\t")
                .append("models.add(new ClassModel(foo.name(), foo.value(), foo.drawable()));")
                .append("\n\t\t\t\t\t")
                .append("}")
                .append("\n\t\t\t\t")
                .append("}")
                .append("\n\t\t\t")
                .append("}")
                .append("\n\t\t")
                .append("return models;")
                .append("\n\t")
                .append("}")
        ;

        builder.append("\n}\n");

        try { // write the file
            JavaFileObject source = processingEnv.getFiler().createSourceFile(
                    "com.ferbajoo.annotationprocessor.generated.GlobalClasses");
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException error) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }

        return true;
    }


    private static Set<Element> getAnnotatedElements(
            Elements elements,
            TypeElement type,
            Class<? extends Annotation> annotation)
    {
        Set<Element> found = new HashSet<Element>();
        for (Element e : elements.getAllMembers(type)) {
            if (e.getAnnotation(annotation) != null)
                found.add(e);
        }
        return found;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new HashSet<>();
        annotations.add(Foo.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
