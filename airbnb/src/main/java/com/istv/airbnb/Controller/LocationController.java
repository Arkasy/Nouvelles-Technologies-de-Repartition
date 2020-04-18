package com.istv.airbnb.Controller;

import com.istv.airbnb.Model.Location;
import com.istv.airbnb.Model.LocationRequest;
import com.istv.airbnb.Model.User;
import com.istv.airbnb.Repository.LocationRepository;
import com.istv.airbnb.Repository.LocationRequestRepository;
import com.istv.airbnb.Repository.UserRepository;
import com.istv.airbnb.Search.LocationSpecification;
import com.istv.airbnb.Search.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/location")
public class LocationController {

//    public DataSource dataSource() {
//        return DataSourceBuilder
//                .create()
//                .username("admin")
//                .password("adminaws")
//                .url("jdbc:mysql://database-1.cckz8215nlew.us-east-1.rds.amazonaws.com:3306/airbnb_spring?useSSL=false&serverTimezone=UTC")
//                .build();
//    }

    @Autowired
    private LocationRepository locationRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private LocationRequestRepository locationRequestRepo;

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) throws SQLException {
        User currentUser = (User) request.getSession().getAttribute("current_user");
        if (isBlackListed(currentUser.getId()) == 0) {
            model.addAttribute("messageClass", "alert alert-warning");
            model.addAttribute("message", "Vous n'êtes pas autorisé à accéder à ces fonctionnalités");
            return "message";
        }
        model.addAttribute("locations", locationRepo.findAllByOwnerNot(currentUser));
        return "/location/index";
    }

    @GetMapping(value = "/show")
    public String show(@RequestParam int id, Model model) {
        Location location = locationRepo.findById(id);
        if (location == null) {
            model.addAttribute("error", "not_found");
        }
        model.addAttribute("location", location);
        return "/location/show";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("location", new Location());
        return "location/new";
    }

/*    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String create(HttpServletRequest request, Model model, @ModelAttribute Location location, RedirectAttributes redirectAttributes) {
        User currentUser = userRepo.findByUsername(request.getUserPrincipal().getName());
        if (currentUser == null) {
            return "redirect:/login";
        }
        location.setOwner(currentUser);
        locationRepo.save(location);
        redirectAttributes.addAttribute("id", location.getId());
        return "redirect:/location/show";

    }*/

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String create(HttpServletRequest request, Model model, @ModelAttribute Location location, BindingResult bindingResult, @RequestParam(name = "image") MultipartFile[] image, RedirectAttributes redirectAttributes) throws IOException, SQLException, IOException {
        User currentUser = userRepo.findByUsername(request.getUserPrincipal().getName());
        if (currentUser == null) {
            return "redirect:/login";
        }
        location.setOwner(currentUser);

        // Set all images in a foreign table
        List<byte[]> list = new ArrayList<byte[]>();
        for (MultipartFile mf : image) {
            byte[] encoded = Base64.getEncoder().encode(mf.getBytes());
            list.add(encoded);
        }
        System.out.println("List is Empty?");
        if (!list.isEmpty()){
            System.out.println("No");
            location.setImages(list);
            System.out.println(location.getImagesString().size());
        }

        // Set the first image uploaded as a "thumbnail image"
        byte[] encodedBytes = Base64.getEncoder().encode(image[0].getBytes());
        location.setImage(encodedBytes);
        locationRepo.save(location);
        redirectAttributes.addAttribute("id", location.getId());
        return "redirect:/location/show";

    }

    @GetMapping(value = "search")
    public String search(HttpServletRequest request, @RequestParam String minPrice, @RequestParam String maxPrice, @RequestParam String city, @RequestParam boolean appartement, @RequestParam boolean studio, @RequestParam boolean house, Model model) {
        User currentUser = userRepo.findByUsername(request.getUserPrincipal().getName());
        ArrayList<LocationSpecification> listSpecs = new ArrayList<>();
        if (!minPrice.equals(null) && !minPrice.equals(""))
            listSpecs.add(new LocationSpecification(new SearchCriteria("price", ">", minPrice)));
        if (!maxPrice.equals(null) && !maxPrice.equals(""))
            listSpecs.add(new LocationSpecification(new SearchCriteria("price", "<", maxPrice)));
        if (!city.equals(null) && !city.equals(""))
            listSpecs.add(new LocationSpecification(new SearchCriteria("city", "%", city)));
        if (appartement)
            listSpecs.add(new LocationSpecification(new SearchCriteria("type", "=", "appartement")));
        if (house)
            listSpecs.add(new LocationSpecification(new SearchCriteria("type", "=", "house")));
        if (studio)
            listSpecs.add(new LocationSpecification(new SearchCriteria("type", "=", "studio")));
        listSpecs.add(new LocationSpecification(new SearchCriteria("owner", "!=", currentUser)));
        Specification spec = Specification.where((listSpecs.size() == 0) ? null : listSpecs.get(0))
                .and((listSpecs.size() <= 1) ? null : listSpecs.get(1))
                .and((listSpecs.size() <= 2) ? null : listSpecs.get(2))
                .and((listSpecs.size() <= 3) ? null : listSpecs.get(3))
                .and((listSpecs.size() <= 4) ? null : listSpecs.get(4))
                .and((listSpecs.size() <= 5) ? null : listSpecs.get(5))
                .and((listSpecs.size() <= 6) ? null : listSpecs.get(6));
        List<Location> list = (List<Location>) locationRepo.findAll(spec);
        if(!list.isEmpty())
            model.addAttribute("locations", list);
        return "location/fragments/_list";
    }

    @RequestMapping(value = "/rentRequest", method = RequestMethod.POST)
    public String rentRequest(HttpServletRequest request, @RequestParam String locationId, Model model) throws InterruptedException, IOException, URISyntaxException {
        User currentUser = userRepo.findByUsername(request.getUserPrincipal().getName());
        System.out.println(locationId);
        if (currentUser == null) {
            return "redirect:/login";
        }
        Location location = locationRepo.findById(Integer.valueOf(locationId));
        if (location == null) {
            model.addAttribute("messageClass", "alert-warning");
            model.addAttribute("message", "Ce bien n'existe pas, vous ne pouvez donc pas le louer.");
            return "message";
        }
        if (location.getOwner().getId() == currentUser.getId()) {
            model.addAttribute("messageClass", "alert-warning");
            model.addAttribute("message", "Vous ne pouvez pas louer votre propre bien.");
            return "message";
        }
        String name = location.getTitle() + " - " + new Date();
        if(debit(name, Integer.valueOf(currentUser.getBankAccount()), location.getPrice()) && credit(name, location.getOwner().getBankAccount(), location.getPrice())){
            LocationRequest locationRequest = new LocationRequest(location, currentUser, new Date(), false);
            locationRequestRepo.save(locationRequest);
            model.addAttribute("message","Transaction effectué : Votre demande de location a bien été enregistrée.");
            return "location/rent_confirmation";
        }else{
            model.addAttribute("message","Transaction annulé : Erreur de paiement.");
            return "location/rent_confirmation";
        }



    }

    @RequestMapping(value = "/bankRequest")
    public String bankRequest(HttpServletRequest request, @RequestParam String id, Model model) {
        User currentUser = userRepo.findByUsername(request.getUserPrincipal().getName());
        if (currentUser == null) {
            return "redirect:/login";
        }
        Location location = locationRepo.findById(Integer.valueOf(id));
        if (location == null) {
            model.addAttribute("messageClass", "alert-warning");
            model.addAttribute("message", "Ce bien n'existe pas, vous ne pouvez donc pas le louer.");
            return "message";
        }
        if (location.getOwner().getId() == currentUser.getId()) {
            model.addAttribute("messageClass", "alert-warning");
            model.addAttribute("message", "Vous ne pouvez pas louer votre propre bien.");
            return "message";
        }
        System.out.println(request.getParameterMap().toString());
        model.addAttribute("user", currentUser);
        return "location/bank_confirmation";
    }


        private int isBlackListed(int userId) throws SQLException {
        return 1;
//        DataSource ds = this.dataSource();
//        String query = "SELECT * from blacklisted_users where idUser = " + userId;
//        Statement sm = ds.getConnection().createStatement();
//        ResultSet rs = sm.executeQuery(query);
//        int enabled = 0;
//        while (rs.next()) {
//            enabled = rs.getInt("enabled");
//        }
//        return enabled;
    }

    private boolean debit(String name, int idAccount, double amount) throws URISyntaxException, IOException, InterruptedException {
        final URI url = new URI("http://localhost:8080/banque/ws");
        String xmlContent = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:gs=\"http://istv.com/banque/operations_webservice\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <gs:AddOperationRequest>\n" +
                "            <gs:name>" + name + "</gs:name>\n" +
                "            <gs:idAccount>" + idAccount + "</gs:idAccount>\n" +
                "            <gs:amount>-" + amount + "</gs:amount>\n" +
                "        </gs:AddOperationRequest>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(xmlContent))
                .build();

        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        if(response.statusCode() == 200){
            System.out.println("operation ajoutée");
            return true;
        }
        else {
            System.out.println("une erreur s'est produite lors de l'opération");
            return false;
        }
    }

    private boolean credit(String name, int idAccount, double amount) throws URISyntaxException, IOException, InterruptedException {
        final URI url = new URI("http://localhost:8080/banque/ws");
        String xmlContent = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:gs=\"http://istv.com/banque/operations_webservice\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <gs:AddOperationRequest>\n" +
                "            <gs:name>" + name + "</gs:name>\n" +
                "            <gs:idAccount>" + idAccount + "</gs:idAccount>\n" +
                "            <gs:amount>" + amount + "</gs:amount>\n" +
                "        </gs:AddOperationRequest>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(xmlContent))
                .build();

        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        if(response.statusCode() == 200){
            System.out.println("operation ajoutée");
            return true;
        }
        else {
            System.out.println("une erreur s'est produite lors de l'opération");
            return false;
        }
    }

}
