package services;

import repositories.AuthenticationRepo;
import transferObjects.PersonVO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Alexander Burghuber
 */
@Path("manage")
public class ManageService {

    /**
     * Inserts the details(firstName, lastName, gender, etc.) of an already existing user
     *
     * @param person the Transfer Object of the Person entity
     * @return a json that includes either the user and person object or an error
     */
    @Path("insertDetails")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertDetails(PersonVO person) {
        return AuthenticationRepo.getInstance().insertDetails(
                person.getEmail(),
                person.getFirstName(),
                person.getLastName(),
                person.getGender(),
                person.getBirthday(),
                person.getHeight(),
                person.getWeight()
        );
    }

    /**
     * Updates the details of a person
     *
     * @param person the Transfer Object of the Person entity
     * @return a json that includes either the user and person object or an error
     */
    @Path("updateDetails")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateDetails(PersonVO person) {
        return AuthenticationRepo.getInstance().updateDetails(
                person.getUsername(),
                person.getEmail(),
                person.getPassword(),
                person.getFirstName(),
                person.getLastName(),
                person.getGender(),
                person.getBirthday(),
                person.getHeight(),
                person.getWeight()
        );
    }

}

