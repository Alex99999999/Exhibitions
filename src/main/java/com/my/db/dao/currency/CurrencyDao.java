package com.my.db.dao.currency;

import com.my.entity.Currency;
import com.my.exception.DBException;
import java.util.List;

public class CurrencyDao {

  private static CurrencyDao instance;
  private static final CurrencyRepo CURRENCY_REPO = CurrencyRepo.getInstance();

  public static synchronized CurrencyDao getInstance() {
    if (instance == null) {
      instance = new CurrencyDao();
    }
    return instance;
  }

  public List<Currency> findAll() throws DBException {
    return CURRENCY_REPO.findAllCurrency();
  }

  public Currency findById(long id) throws DBException {
    return CURRENCY_REPO.findCurrencyById(id);

  }
}
