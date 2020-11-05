package com.example.doctorappointment;

public class DaoSession  extends AbstractDaoSession {

    private final DaoConfig addressBookDaoConfig;

    private final AddressBookDaoextends addressBookDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        addressBookDaoConfig = daoConfigMap.get(AddressBookDaoextends.class).clone();
        addressBookDaoConfig.initIdentityScope(type);

        addressBookDao = new AddressBookDao(addressBookDaoConfig, this);

        registerDao(AddressBook.class, addressBookDao);
    }

    public void clear() {
        addressBookDaoConfig.clearIdentityScope();
    }

    public AddressBookDao getAddressBookDao() {
        return addressBookDao;
    }

    {
    }
}
