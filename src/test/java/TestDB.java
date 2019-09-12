import config.DTO.TestDTO;
import config.Entities.User;
import config.repositories.TestRepository;
import config.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class TestDB {

    @Autowired
    TestRepository tr;
    @Test
    public void WhetherWeGetUserbyUsername()
    {
        TestDTO tdto = new TestDTO(tr.findAll());
        //tdto.getQuestions();
    }
}
