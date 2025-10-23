package com.rocio.aad.repository;

import com.rocio.aad.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class ModuleRepository implements CrudRepository<Student> {

    //Clase de repositorio. Clase para manejar los datos

    /**
     * @param entity
     * @return
     */
    @Override
    public Student create(Student entity) {
        log.info("Create: {}", entity.toString());
        return entity;
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public Student read(Student entity) {
        log.info("Read: {}", entity.toString());
        return entity;
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public Student update(Student entity) {
        log.info("Update: {}", entity.toString());
        return entity;
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public boolean delete(Student entity) {
        log.info("Delete: {}", entity.toString());
        return true;
    }


}
