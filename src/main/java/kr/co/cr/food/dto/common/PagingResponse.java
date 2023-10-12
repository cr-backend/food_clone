package kr.co.cr.food.dto.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
public class PagingResponse<T> {
  private MetaData metaData;
  private List<T> data;

  public static <T>PagingResponse<T> create(Page page, List<T> data) {
    PagingResponse<T> pagingResponse = new PagingResponse<>();
    MetaData meta = new MetaData();
    meta.setPage(page.getNumber());
    meta.setMaxPage(page.getTotalPages());

    pagingResponse.setMetaData(meta);
    pagingResponse.setData(data);
    return pagingResponse;
  }
}
