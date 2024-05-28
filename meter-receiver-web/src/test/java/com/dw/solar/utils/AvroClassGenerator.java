package com.dw.solar.utils;

import org.apache.avro.Schema;
import org.apache.avro.compiler.specific.SpecificCompiler;

import java.io.File;

public class AvroClassGenerator {

    public static void generateAvroClasses() throws Exception {
        SpecificCompiler compiler = new SpecificCompiler(new Schema.Parser().parse(new File("solarmeter.avsc")));
        compiler.compileToDestination(new File("src/main/resources"), new File("src/main/java"));
    }

    public static void main(String[] args) {
        try {
            AvroClassGenerator.generateAvroClasses();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
