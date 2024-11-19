package com.example.Integradoraturismo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Integradoraturismo.exception.ResourceNotFoundException;
import com.example.Integradoraturismo.models.EmpresaMiembro;
import com.example.Integradoraturismo.repository.EmpresaMiembroRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/")
@Validated
@RequiredArgsConstructor
public class EmpresaMiembroController {

    private final EmpresaMiembroRepository empresaMiembroRepository;

    // Constructor para inyectar el repositorio

    /**
     * Devuelve una lista con todos los registros de la tabla Empresa Miembro
     *
     * @return una lista de EmpresaMiembro
     */
    @GetMapping("/empresamiembro")
    public List<EmpresaMiembro> getAllEmpresaMiembros() {
        return empresaMiembroRepository.findAll();
    }

    /**
     * Devuelve un registro de la tabla Empresa Miembro filtrando por el id
     *
     * @param id el id del registro a buscar
     * @return el registro encontrado con el id especificado
     * @throws ResourceNotFoundException si no se encuentra el elemento con el
     * id especificado
     */
    @GetMapping("/empresamiembro/{id}")
    public ResponseEntity<EmpresaMiembro> getEmpresaMiembroById(@PathVariable long id) {
        EmpresaMiembro empresaMiembro = empresaMiembroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el elemento con id: " + id));
        return ResponseEntity.ok(empresaMiembro);
    }

    /**
     * Crea un nuevo registro en la tabla Empresa Miembro.
     * <p>
     * El cuerpo de la peticion debe contener un objeto EmpresaMiembro con los
     * campos que se desean insertar.
     * <p>
     * El metodo devuelve un objeto con el registro creado, con un estado 201
     * Created.
     **/
    @PostMapping("/empresamiembro")
    public ResponseEntity<EmpresaMiembro> createEmpresaMiembro(@Valid @RequestBody EmpresaMiembro empresaMiembro) {
        EmpresaMiembro nuevaEmpresaMiembro = empresaMiembroRepository.save(empresaMiembro);
        return new ResponseEntity<>(nuevaEmpresaMiembro, HttpStatus.CREATED);  // Retorna 201 Created
    }

    /**
     * Actualiza un registro en la tabla Empresa Miembro.
     * <p>
     * El id del registro a actualizar se pasa como parametro en la ruta.
     * El cuerpo de la peticion debe contener un objeto EmpresaMiembro con los
     * campos que se desean actualizar.
     * <p>
     * El metodo devuelve un objeto con el registro actualizado, con un estado 200
     * Ok.
     * <p>
     * Si no se encuentra el elemento con el id especificado se devuelve un estado
     * 404 Not Found.
     */
    @PutMapping("/empresamiembro/{id}")
    public ResponseEntity<EmpresaMiembro> updateEmpresaMiembro(@PathVariable Long id, @Valid @RequestBody EmpresaMiembro empresaMiembroUpdate) {
        EmpresaMiembro empresaMiembro = empresaMiembroRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el elemento con id: " + id));
        
        // Actualizar campos
        empresaMiembro.setNombre(empresaMiembroUpdate.getNombre());
        empresaMiembro.setEmail(empresaMiembroUpdate.getEmail());
        empresaMiembro.setTelefono(empresaMiembroUpdate.getTelefono());
        empresaMiembro.setDireccion(empresaMiembroUpdate.getDireccion());
        
        EmpresaMiembro empresaActualizada = empresaMiembroRepository.save(empresaMiembro);
        return ResponseEntity.ok(empresaActualizada);
    }

    /**
     * Elimina un registro de la tabla Empresa Miembro en la base de datos.
     * <p>
     * Si el registro no existe, se lanza una excepción
     * {@link ResourceNotFoundException}.
     * <p>
     * El método devuelve un objeto con una sola propiedad llamada "deleted" con
     * valor true si el registro se eliminá correctamente.
     *
     * @param id el id del registro a eliminar
     * @return un objeto con una sola propiedad llamada "deleted" con valor true
     * si el registro se elimin correctamente
     * @throws ResourceNotFoundException si no se encuentra el elemento con el
     * id especificado
     */
    @DeleteMapping("/empresamiembro/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmpresaMiembro(@PathVariable Long id) {
        EmpresaMiembro empresaMiembro = empresaMiembroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el elemento con id: " + id));
        empresaMiembroRepository.delete(empresaMiembro);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);

    }

}
