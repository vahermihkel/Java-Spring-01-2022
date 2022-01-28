package ee.mihkel.webshop;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class MockController {
    List<String> strings = new ArrayList<>(Arrays.asList("Üks", "Kaks"));

    // GET localhost:8080/strings
    @GetMapping("strings")
    public List<String> getStrings() {
        return strings;
    }

    // POST localhost:8080/strings   JA pean kaasa saatma body string kujul
    @PostMapping("strings")
    public String addString(@RequestBody String newString) {
        strings.add(newString);
        return "Edukalt lisatud uus string: " + newString;
    }

    // DELETE localhost:8080/strings/1
    @DeleteMapping("strings/{id}")
    public String deleteString(@PathVariable int id) {
        strings.remove(id);
        return "Edukalt kustutatud string id-ga:" + id;
    }

    // DELETE localhost:8080/strings
    @DeleteMapping("strings")
    public String deleteAllStrings() {
        strings.clear();
        return "Edukalt kustutatud kõik stringid" ;
    }

    // PUT localhost:8080/strings/{id} ... vaja ka Body
    @PutMapping("strings/{id}")
    public String editString(@PathVariable int id, @RequestBody String string) {
        strings.set(id,string);
        return "Edukalt muudetud string id-ga: " + id;
    }

    // GET localhost:8080/validate-user
//    @GetMapping("validate-user")
//    public boolean validateUser() {
//        return true;
//    }
}
