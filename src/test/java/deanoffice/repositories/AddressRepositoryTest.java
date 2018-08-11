package deanoffice.repositories;

import deanoffice.entities.Address;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressRepositoryTest {
    @Autowired
    private AddressRepository addressRepository;
    @Before
    public void setUp() throws Exception{
        Address address1 = new Address(
                "Łódź",
                "Aleja Politechniki",
                "30b",
                "8"
        );
        Address address2 = new Address(
                "Warszawa",
                "Aleje Jerozolimskie",
                "123a",
                "12"
        );

        Assert.assertNull(address1.getId());
        Assert.assertNull(address2.getId());

        this.addressRepository.save(address1);
        this.addressRepository.save(address2);

        Assert.assertNotNull(address1.getId());
        Assert.assertNotNull(address1.getId());
    }
    @Test
    public void testFetchData(){
        Address address = addressRepository.findByCity("Łódź");
        Assert.assertNotNull(address);
        Iterable<Address> addresses = addressRepository.findAll();
        for(Address a : addresses){
            System.out.println(a);
        }
    }
}
