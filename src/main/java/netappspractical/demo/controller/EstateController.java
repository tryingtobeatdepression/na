package netappspractical.demo.controller;

import netappspractical.demo.domain.Estate;
import netappspractical.demo.dto.EstateDto;
import netappspractical.demo.repository.EstateRepository;
import netappspractical.demo.repository.ParameterRepository;
import netappspractical.demo.util.Strings;
import netappspractical.demo.util.Utility;
import org.apache.tomcat.util.json.JSONParser;
import org.codehaus.jettison.json.JSONStringer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("/estates")
public class EstateController {

    @Autowired
    private EstateRepository estateRepository;

    @Autowired
    private ParameterRepository parameterRepository;

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
    public ResponseEntity<?> delete(@PathVariable int id) {
        this.estateRepository.deleteById(id);
        return ResponseEntity.ok("Estate deleted.");
    }


    /**
     * Edit Estate info.
     *
     * @param id
     * @param estateDto
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody EstateDto estateDto) {
        try {
            Optional<Estate> estate = this.estateRepository.findById(id);
            setEstateValues(estate.get(), estateDto);
            // We must not update or increment the "version" attribute.
            // Only the persistence provider can do that, so data stays consistent.
            this.estateRepository.save(estate.get());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed!");
        }
        return ResponseEntity.ok("Estate information has been edited.");
    }

    /**
     * Sell an Estate.
     *
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("/sell/{id}")
    public ResponseEntity<?> sellEstate(@PathVariable int id, @RequestBody EstateDto dto) {
        try {
            Optional<Estate> estate = this.estateRepository.findById(id);
            // Get buyerName, sellingDate
            sell(estate.get(), dto);
            // We must not update or increment the "version" attribute.
            // Only the persistence provider can do that, so data stays consistent.
            this.estateRepository.save(estate.get());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed!");
        }
        return ResponseEntity.ok("Estate has been sold.");
    }

    /**
     * Index page can be provided with a query to find the estate by name.
     * TESTED ✅
     * @param query
     * @return
     */
    @GetMapping
    public ResponseEntity<?> index(@RequestParam(required = false) String query) {
        List<Estate> list = this.estateRepository.findAll()
                    .stream()
                    .filter(Utility.query(query))
                    .collect(Collectors.toList());
        return ResponseEntity.ok(list.isEmpty() ? Strings.NO_DATA_FOUND : list);
    }

    /**
     * Get unsold Estates.
     * TESTED ✅
     * @return
     */
    @GetMapping("/unsold")
    public ResponseEntity<?> getUnsold() {
        List<Estate> list = this.estateRepository.findAll()
                .stream()
                .filter(Utility.unsoldEstates)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list.isEmpty() ? Strings.NO_DATA_FOUND : list);
    }

    /**
     * Set the values of the estate object using EstateDto.
     *
     * @param estate
     * @param dto
     */
    private void setEstateValues(Estate estate, EstateDto dto) {
        estate.setSold(false);
        if (dto.getName() != null)
            estate.setName(dto.getName());
        if (dto.getPrice() != null) {
            // Default price
            Double profitPercentage = this.parameterRepository.
                    findBypKey("profitPercentage").get().getPValue();
            Double defaultPrice = (profitPercentage * dto.getPrice()) +
                    dto.getPrice();
            estate.setPrice(defaultPrice);
        }
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
        estate.setSold(true);
        if (dto.getBuyerName() != null)
            estate.setBuyerName(dto.getBuyerName());
        if (dto.getDateOfSelling() != null)
            estate.setDateOfSelling(dto.getDateOfSelling());
    }
}
