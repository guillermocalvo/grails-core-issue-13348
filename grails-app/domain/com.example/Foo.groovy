package com.example

import groovy.transform.CompileStatic

@CompileStatic
class Foo extends Lorem {

    String bar

    def afterLoad() {
        System.err.println("AFTER LOAD -- FOO [$world, $ipsum, $bar]")
    }
}
