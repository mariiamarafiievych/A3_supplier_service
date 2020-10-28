package supplier.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import supplier.entities.Item;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    @Query("SELECT th FROM Item th WHERE th.name = :name")
    Item findItemByName(@Param("name") String name);

    @Query("SELECT th FROM Item th INNER JOIN Storage s ON th.id = s.item WHERE s.quantity > 0")
    List<Item> getItemsInStock();
}
