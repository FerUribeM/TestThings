package com.testthings.processor;

import com.ferbajoo.annotation.Foo;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class ClassProcessor extends AbstractProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment e) {
        //get all elements annotated with AwesomeLogger
        Collection<? extends Element> annotatedElements = e.getElementsAnnotatedWith(Foo.class);

        StringBuilder builder = new StringBuilder()
                .append("package\t")
                .append("com.ferbajoo.testthings")
                .append(";\n\n")
                .append("import\t")
                .append("com.ferbajoo.testthings.models.ClassModel")
                .append(";\n")
                .append("import\t")
                .append(com.ferbajoo.annotation.Foo.class.getCanonicalName()) //get package from class Foo
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
                .append("\n\t")
                .append("public static ArrayList<ClassModel> getAllClasses() {")
                .append("\n\t\t")
                .append("ArrayList<ClassModel> models = new ArrayList<>();");

        for (Element type : annotatedElements) {
            Foo foo = type.getAnnotation(Foo.class);
            builder
                    .append("\n\t\t")
                    .append("models.add(new ClassModel(\""
                            + foo.name() + "\""
                            + ",\"" + foo.value() + "\","
                            + String.valueOf(foo.drawable()) + ",\""
                            + type.toString()
                            + "\"));")
            ;
        }
        builder
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
