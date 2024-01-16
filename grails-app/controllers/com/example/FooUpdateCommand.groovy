package com.example

import grails.validation.Validateable

class FooUpdateCommand implements Validateable {

    Long id
    String world
    String ipsum
    String bar

    static constraints = {
        id nullable: false
        world nullable: false
        ipsum nullable: false
        bar nullable: false
    }
}
