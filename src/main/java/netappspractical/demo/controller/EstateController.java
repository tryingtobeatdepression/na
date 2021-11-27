package netappspractical.demo.controller;

import netappspractical.demo.domain.Estate;
import netappspractical.demo.dto.EstateDto;
import netappspractical.demo.repository.EstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/estates")
public class EstateController {

    @Autowired
    private EstateRepository estateRepository;

    /**
     * Create a new Estate entity on post request.
     *
     * @param estateDto
     * @return
     */
    @PostMapping("/create")
    public @ResponseBody String create(@RequestBody EstateDto estateDto) {
        Estate estate = new Estate();
        setEstateValues(estate, estateDto);
        this.estateRepository.save(estate);
        return "Estate created.";
    }

    /**
     * Delete an Estate entity on delete request.
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public @ResponseBody String delete(@PathVariable int id) {
        this.estateRepository.deleteById(id);
        return "Estate deleted.";
    }

    @PutMapping("/{id}")
    public @ResponseBody String edit(@PathVariable int id, @RequestBody EstateDto estateDto)
            throws IllegalAccessException, NoSuchFieldException {
        Estate estate = this.estateRepository.getById(id);
        setEstateValues(estate, estateDto);
        this.estateRepository.save(estate);
        return "Estate information has been edited.";
    }

    /**
     * Index page can be provided with a query to find the estate by name.
     *
     * @param query
     * @return
     */
    @GetMapping
    public ResponseEntity<?> index(@RequestParam(required = false) String query) {
        if(query != null) {
            List<Estate> estates = new ArrayList<>();
            for (Estate estate : this.estateRepository.findAll()) {
                if (estate.getName().toUpperCase().contains(query.toUpperCase()))
                    estates.add(estate);
            }
            return ResponseEntity.ok(estates);
        }
        return ResponseEntity.ok("No records found.");
    }

    /**
     * Set the values of the estate object using EstateDto.
     *
     * @param estate
     * @param dto
     */
    private void setEstateValues(Estate estate, EstateDto dto) {
        if(dto.getName() != null)
            estate.setName(dto.getName());
        if(dto.getDateOfSelling() != null)
            estate.setDateOfSelling(dto.getDateOfSelling());
        if(dto.getPrice() != null)
            estate.setPrice(dto.getPrice());
        if(dto.getBuyerName() != null)
            estate.setBuyerName(dto.getBuyerName());
        if(dto.getShareCount() != null)
            estate.setShareCount(dto.getShareCount());
    }
}
