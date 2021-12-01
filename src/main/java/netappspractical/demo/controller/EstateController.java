package netappspractical.demo.controller;

import netappspractical.demo.domain.Estate;
import netappspractical.demo.dto.EstateDto;
import netappspractical.demo.repository.EstateRepository;
import netappspractical.demo.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public @ResponseBody
    String create(@RequestBody EstateDto estateDto) {
        Estate estate = new Estate();
        setEstateValues(estate, estateDto);
        estate.setVersion(estateDto.getVersion());
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
    public @ResponseBody
    String delete(@PathVariable int id) {
        this.estateRepository.deleteById(id);
        return "Estate deleted.";
    }


    /**
     * Edit Estate info.
     *
     * @param id
     * @param estateDto
     * @return
     */
    @PutMapping("/{id}")
    public @ResponseBody
    String edit(@PathVariable int id, @RequestBody EstateDto estateDto) {
        Optional<Estate> estate = this.estateRepository.findById(id);
        setEstateValues(estate.get(), estateDto);
        // We must not update or increment the "version" attribute.
        // Only the persistence provider can do that, so data stays consistent.
        this.estateRepository.save(estate.get());
        return "Estate information has been edited.";
    }

    /**
     * Sell an Estate.
     *
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("/sell/{id}")
    public @ResponseBody
    String sellEstate(@PathVariable int id, @RequestBody EstateDto dto) {
        Optional<Estate> estate = this.estateRepository.findById(id);
        sell(estate.get(), dto);
        // We must not update or increment the "version" attribute.
        // Only the persistence provider can do that, so data stays consistent.
        this.estateRepository.save(estate.get());
        return "Estate has been sold.";
    }

    /**
     * Index page can be provided with a query to find the estate by name.
     *
     * @param query
     * @return
     */
    @GetMapping
    public ResponseEntity<?> index(@RequestParam(required = false) String query) {
        if(this.estateRepository.findAll().isEmpty())
            return ResponseEntity.ok(Strings.NO_DATA_FOUND);
        List<Estate> estates = new ArrayList<>();
        if (query != null) {
            for (Estate estate : this.estateRepository.findAll()) {
                if (estate.getName().toUpperCase().contains(query.toUpperCase()))
                    estates.add(estate);
            }
        } else
            estates = this.estateRepository.findAll();
        return ResponseEntity.ok(estates);
    }

    /**
     * Get unsold Estates.
     *
     * @return
     */
    @GetMapping("/unsold")
    public ResponseEntity<?> getUnsold() {
        List<Estate> estates = new ArrayList<>();
        for (Estate estate : this.estateRepository.findAll())
            if (estate.getDateOfSelling() == null)
                estates.add(estate);
        return estates != null ? ResponseEntity.ok(estates) :
                ResponseEntity.ok(Strings.NO_DATA_FOUND);
    }

    /**
     * Set the values of the estate object using EstateDto.
     *
     * @param estate
     * @param dto
     */
    private void setEstateValues(Estate estate, EstateDto dto) {
        if (dto.getName() != null)
            estate.setName(dto.getName());
        if (dto.getPrice() != null)
            estate.setPrice(dto.getPrice());
        if (dto.getShareCount() != null)
            estate.setShareCount(dto.getShareCount());
    }

    /**
     * Set the values of buyerName and dateOfSelling.
     *
     * @param estate
     * @param dto
     */
    private void sell(Estate estate, EstateDto dto) {
        if (dto.getBuyerName() != null)
            estate.setBuyerName(dto.getBuyerName());
        if (dto.getDateOfSelling() != null)
            estate.setDateOfSelling(dto.getDateOfSelling());
    }
}
