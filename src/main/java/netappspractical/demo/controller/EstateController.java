package netappspractical.demo.controller;

import netappspractical.demo.domain.Estates;
import netappspractical.demo.dto.EstateDto;
import netappspractical.demo.repository.EstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;

@RestController
@RequestMapping("/estates")
public class EstateController {

    @Autowired
    private EstateRepository estateRepository;

    @GetMapping
    public @ResponseBody String index() {
        return "This is Estate home page.";
    }

    /**
     * Create a new Estate entity on post request.
     *
     * @param estateDto
     * @return
     */
    @PostMapping("/create")
    public @ResponseBody String create(@RequestBody EstateDto estateDto) {
        Estates estate = new Estates();
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
        Estates estate = this.estateRepository.getById(id);
        setEstateValues(estate, estateDto);
        this.estateRepository.save(estate);
        return "Estate information has been edited.";
    }

    /**
     * Set the values of the estate object using EstateDto.
     *
     * @param estate
     * @param dto
     */
    private void setEstateValues(Estates estate, EstateDto dto) {
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
