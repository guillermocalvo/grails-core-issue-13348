package com.example

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap
import groovy.util.logging.Slf4j

interface IFooService {

    Foo save(String world, String ipsum, String bar)

    Foo update(Serializable id, String world, String ipsum, String bar)

    Foo delete(Serializable id)

    int count()
}

@SuppressWarnings('AbstractClassWithoutAbstractMethod')
@Slf4j
@Service(Foo)
abstract class FooService implements IFooService {

    @Transactional(readOnly = true)
    List<Foo> list(GrailsParameterMap params) {
        Foo.list(params)
    }

    @Transactional(readOnly = true)
    Foo create(GrailsParameterMap params) {
        new Foo(params)
    }

    @Transactional(readOnly = true)
    Foo read(Long id) {
        Foo.read(id)
    }

    @Transactional
    Foo save(FooSaveCommand cmd) {
        save(cmd.world, cmd.ipsum, cmd.bar)
    }

    @Transactional
    Foo update(FooUpdateCommand cmd) {
        update(cmd.id, cmd.world, cmd.ipsum, cmd.bar)
    }
}
