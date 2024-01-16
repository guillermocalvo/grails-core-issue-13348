package com.example

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import org.springframework.context.MessageSource

import static org.springframework.http.HttpStatus.*

@CompileStatic
class FooController {

    static allowedMethods = [save: 'POST', update: 'PUT', delete: 'DELETE']

    FooService fooService

    MessageSource messageSource

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        List<Foo> fooList = fooService.list(params)
        respond fooList, model: [fooCount: fooService.count()]
    }

    def show(Long id) {
        if (!id) {
            notFound()
            return
        }
        respond fooService.read(id)
    }

    def create() {
        respond fooService.create(params)
    }

    def edit(Long id) {
        if (!id) {
            notFound()
            return
        }
        respond fooService.read(id)
    }

    @CompileDynamic
    def save(FooSaveCommand cmd) {
        if (cmd.hasErrors()) {
            respond cmd.errors, [model: [foo: cmd], view: 'create']
            return
        }

        Foo foo = fooService.save(cmd)
        if (foo.hasErrors()) {
            respond foo.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                String msg = messageSource.getMessage('foo.label', [] as Object[], 'Foo', request.locale)
                flash.message = messageSource.getMessage('default.created.message', [msg, foo.id] as Object[], 'Foo created', request.locale)
                redirect(action: 'show', id: foo.id)
            }
            '*' { respond foo, [status: CREATED] }
        }
    }

    @CompileDynamic
    def update(FooUpdateCommand cmd) {
        if (!cmd.id) {
            notFound()
            return
        }

        if (cmd.hasErrors()) {
            respond cmd.errors, [model: [foo: cmd], view: 'edit']
            return
        }

        Foo foo = fooService.update(cmd)
        if (foo == null) {
            notFound()
            return
        }
        request.withFormat {
            form multipartForm {
                String msg = messageSource.getMessage('foo.label', [] as Object[], 'Foo', request.locale)
                flash.message = messageSource.getMessage('default.updated.message', [msg, foo.id] as Object[], 'Student updated', request.locale)
                redirect(action: 'show', id: foo.id)
            }
            '*' { respond foo, [status: OK] }
        }
    }

    @CompileDynamic
    def delete(Long id) {
        if (!id) {
            notFound()
            return
        }

        Foo foo = fooService.delete(id)
        if (!foo) {
            notFound()
            return
        }

        request.withFormat {
            form multipartForm {
                String msg = messageSource.getMessage('foo.label', [] as Object[], 'Foo', request.locale)
                flash.message = messageSource.getMessage('default.deleted.message', [msg, foo.id] as Object[], 'Foo Deleted', request.locale)
                redirect(action: 'index', method: 'GET')
            }
            '*' { render status: NO_CONTENT }
        }
    }

    @CompileDynamic
    protected void notFound() {
        request.withFormat {
            form multipartForm {
                String msg = messageSource.getMessage('foo.label', [] as Object[], 'Foo', request.locale)
                flash.message = messageSource.getMessage('default.not.found.message', [msg, params.id] as Object[], 'Foo not found', request.locale)
                redirect(action: 'index', method: 'GET')
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
