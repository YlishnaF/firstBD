import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.ReadContext;
import org.junit.Assert;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;
import static com.jayway.jsonpath.JsonPath.parse;
import static org.junit.internal.matchers.StringContains.containsString;


public class DBIntro {

    public static void main(String[] args) {
        Connection connection = new ConnectionClass().getFileFromResources();
        DbRespondHandler respondPeople = new DbRespondHandler("SELECT * FROM peopleandpets.people", connection);

        DbRespondHandler respondPeopleAndPets = new DbRespondHandler("SELECT peopleandpets.people.name, peopleandpets.people.location, peopleandpets.people.birthday, peopleandpets.animals.pet_name, peopleandpets.animals.type,peopleandpets.animals.pet_birthday\n" +
                "from peopleandpets.people\n" +
                "join peopleandpets.peopleanimals on peopleandpets.people.id=peopleandpets.peopleanimals.id_people\n" +
                "join peopleandpets.animals on peopleandpets.peopleanimals.id_animal=peopleandpets.animals.id", connection);
        respondPeopleAndPets.writeRespondToFile("jsonResp.json");

        ReadContext ctx2 = parse(respondPeopleAndPets.getArray());
        List<String> pAp = ctx2.read("$[?(@.pet_name==\"Vasiliy\")]");
        System.out.println("Владельцы Василия: "+pAp);

        Filter filter = filter(where("pet_name").is("Vasiliy").and("birthday").is("1986-02-10"));
        List<Map<String, Object>> personsAndVasiliy = ctx2.read("$.[?]", filter);
        System.out.println("Владельцы Василия, рожденные 1986-02-10: "+personsAndVasiliy);
        Assert.assertEquals(personsAndVasiliy.get(0).getOrDefault("birthday", ""), "1986-02-10");
        Assert.assertEquals(personsAndVasiliy.get(0).getOrDefault("name", ""), "Иванов И.И.");

        ReadContext ctx = parse(respondPeople.getArray());
        List<String> persons = ctx.read("$[?(@.name==\"Иванов И.И.\")]");
        System.out.println("Люди с именем  = Иванов И.И.: " + persons);


        Filter idFilter = filter(
                where("id").is("2")
        );

        List<Map<String, Object>> personsInfo = ctx.read("$.[?]", idFilter);
        Assert.assertThat(personsInfo.toString(), containsString("г Москва ул Ленина 10"));
        Assert.assertEquals(personsInfo.get(0).getOrDefault("birthday", ""), "1984-05-22");
        System.out.println("Люди с id = 2: " + personsInfo);

        Filter filterId2 = filter(
                where("id").gt(2)
        );
        List<Map<String, Object>> personsFilter2 = ctx.read("$.[?]", filterId2);
        System.out.printf("Люди с id больше 2: " + personsFilter2.toString());


        ConnectionClass.close(connection);


    }

}
