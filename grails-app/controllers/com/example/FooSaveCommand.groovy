package com.example

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class FooSaveCommand implements Validateable {

    String world
    String ipsum
    String bar

    static constraints = {
        world nullable: false
        ipsum nullable: false
        bar nullable: false
    }
}
