package netappspractical.demo.controller;

import netappspractical.demo.domain.Estates;
import netappspractical.demo.dto.EstateDto;
import netappspractical.demo.repository.EstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        estate.setName(estateDto.getName());
        estate.setPrice(estateDto.getPrice());
        estate.setShareCount(estateDto.getShareCount());

        if (estateDto.getBuyerName() != null)
            estate.setBuyerName(estateDto.getBuyerName());
        if(estateDto.getDateOfSelling() != null)
            estate.setDateOfSelling(estateDto.getDateOfSelling());

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
}
