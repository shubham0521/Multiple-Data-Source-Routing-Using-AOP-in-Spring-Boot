package database.dynamic.routing.dynamic.routing.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shubhamkumar
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductModel {
  private String id;
  private String name;
}
