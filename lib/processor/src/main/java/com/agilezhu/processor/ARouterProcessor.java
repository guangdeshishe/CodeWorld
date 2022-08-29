package com.agilezhu.processor;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Generate ARouterPage.java for ARouter
 */
@AutoService(Processor.class)
public class ARouterProcessor extends AbstractProcessor {
    private Filer mFiler; //文件相关工具类：用于保存生成的java类文件
    private Elements mElementUtils; //元素相关工具类：用于获取java类文件
    private Messager mMessager;//用于打印日志

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        //返回该注解处理器能够处理哪些注解
        Set<String> types = new LinkedHashSet<>();
        types.add(Route.class.getName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        //返回当前注解处理器支持的java版本号
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set == null || set.size() == 0) {
            return false;
        }
        //将要生成的java完整类名
        String targetClassName = "ARouterPage";
        String packageName = "com.agilezhu.processor.generate";

        //创建方法(构造方法)
        MethodSpec.Builder bindMethodBuilder = MethodSpec.methodBuilder("<init>")
                .addModifiers(Modifier.PUBLIC);

        //创建类
        TypeSpec.Builder aRouterPageClassBuilder = TypeSpec.classBuilder(targetClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        //获取所有的源码文件
        Set<? extends Element> elements = roundEnvironment.getRootElements();
        for (Element element : elements) {
            if (!(element instanceof TypeElement)) {//判断是否class类
                continue;
            }
            //转换成class类型
            TypeElement typeElement = (TypeElement) element;
            //当前文件的类名
            String classSimpleName = element.getSimpleName().toString();
            Route routeAnnotation = element.getAnnotation(Route.class);
            if (routeAnnotation == null) {
                continue;
            }
            mMessager.printMessage(Diagnostic.Kind.WARNING, "myProcess=====>>>>>" + classSimpleName);

            ClassName innerClassName = ClassName.get(packageName, targetClassName, classSimpleName);

            try {
                aRouterPageClassBuilder.addField(innerClassName, classSimpleName)
                        .addType(TypeSpec.classBuilder(innerClassName.simpleName())
                                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                                .addField(String.class, "path", Modifier.PUBLIC, Modifier.STATIC)
                                .addStaticBlock(CodeBlock.builder().addStatement("path= \"" + routeAnnotation.path() + "\"").build())
                                .addMethod(MethodSpec
                                        .methodBuilder("navigation")
                                        .addParameter(String.class,"key")
                                        .addParameter(ClassName.get("android.os","Bundle"), "params")
                                        .addStatement("com.alibaba.android.arouter.launcher.ARouter.getInstance().build(path).withBundle(key,params).navigation()")
                                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                        .build())
                                .addMethod(MethodSpec
                                        .methodBuilder("navigation")
                                        .addStatement("com.alibaba.android.arouter.launcher.ARouter.getInstance().build(path).navigation()")
                                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                        .build())
                                .build());
            } catch (Exception e) {
                e.printStackTrace();
            }

            //构造方法中添加初始化代码
            bindMethodBuilder.addStatement(classSimpleName + ".path = \"" + routeAnnotation.path() + "\"");
        }
        aRouterPageClassBuilder.addMethod(bindMethodBuilder.build());
        //创建java文件
        JavaFile bindProxyFile = JavaFile
                .builder(packageName, aRouterPageClassBuilder.build())
                .build();
        try {
            //保存java类文件
            bindProxyFile.writeTo(mFiler);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

}


