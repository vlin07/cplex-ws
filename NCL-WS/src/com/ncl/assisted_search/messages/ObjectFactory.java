
package com.ncl.assisted_search.messages;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ncl.assisted_search.messages package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetAssistedSearchRecommendationFlexDatesSet_QNAME = new QName("", "flexDatesSet");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ncl.assisted_search.messages
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DestinationSet }
     * 
     */
    public DestinationSet createDestinationSet() {
        return new DestinationSet();
    }

    /**
     * Create an instance of {@link PromoCodeSet }
     * 
     */
    public PromoCodeSet createPromoCodeSet() {
        return new PromoCodeSet();
    }

    /**
     * Create an instance of {@link ThingsToDoRecommendation }
     * 
     */
    public ThingsToDoRecommendation createThingsToDoRecommendation() {
        return new ThingsToDoRecommendation();
    }

    /**
     * Create an instance of {@link DeparturePortSet }
     * 
     */
    public DeparturePortSet createDeparturePortSet() {
        return new DeparturePortSet();
    }

    /**
     * Create an instance of {@link FlexDatesSet }
     * 
     */
    public FlexDatesSet createFlexDatesSet() {
        return new FlexDatesSet();
    }

    /**
     * Create an instance of {@link DurationSet }
     * 
     */
    public DurationSet createDurationSet() {
        return new DurationSet();
    }

    /**
     * Create an instance of {@link AccomodationSet }
     * 
     */
    public AccomodationSet createAccomodationSet() {
        return new AccomodationSet();
    }

    /**
     * Create an instance of {@link PriceRangeSet }
     * 
     */
    public PriceRangeSet createPriceRangeSet() {
        return new PriceRangeSet();
    }

    /**
     * Create an instance of {@link InterestSet }
     * 
     */
    public InterestSet createInterestSet() {
        return new InterestSet();
    }

    /**
     * Create an instance of {@link RecommReqLimitSet }
     * 
     */
    public RecommReqLimitSet createRecommReqLimitSet() {
        return new RecommReqLimitSet();
    }

    /**
     * Create an instance of {@link UserWeightSet }
     * 
     */
    public UserWeightSet createUserWeightSet() {
        return new UserWeightSet();
    }

    /**
     * Create an instance of {@link GetAssistedSearchRecommendationResponse }
     * 
     */
    public GetAssistedSearchRecommendationResponse createGetAssistedSearchRecommendationResponse() {
        return new GetAssistedSearchRecommendationResponse();
    }

    /**
     * Create an instance of {@link CruisePackageRecommendation }
     * 
     */
    public CruisePackageRecommendation createCruisePackageRecommendation() {
        return new CruisePackageRecommendation();
    }

    /**
     * Create an instance of {@link GetAssistedSearchRecommendation }
     * 
     */
    public GetAssistedSearchRecommendation createGetAssistedSearchRecommendation() {
        return new GetAssistedSearchRecommendation();
    }

    /**
     * Create an instance of {@link ConstraintViolation }
     * 
     */
    public ConstraintViolation createConstraintViolation() {
        return new ConstraintViolation();
    }

    /**
     * Create an instance of {@link ThingToDoSet }
     * 
     */
    public ThingToDoSet createThingToDoSet() {
        return new ThingToDoSet();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FlexDatesSet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "flexDatesSet", scope = GetAssistedSearchRecommendation.class)
    public JAXBElement<FlexDatesSet> createGetAssistedSearchRecommendationFlexDatesSet(FlexDatesSet value) {
        return new JAXBElement<FlexDatesSet>(_GetAssistedSearchRecommendationFlexDatesSet_QNAME, FlexDatesSet.class, GetAssistedSearchRecommendation.class, value);
    }

}
