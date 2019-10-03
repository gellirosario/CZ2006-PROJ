package com.cz2006.helloworld.util;

import com.cz2006.helloworld.models.MapDetail;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.kml.KmlContainer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XMLSaxHandler extends DefaultHandler {

    private StringBuilder str;
    private ArrayList<MapDetail> details;
    private MapDetail detail;
    private boolean
            isName = false,
            isAddressPostalCode = false,
            isAddressUnitNumber = false,
            isAddressBuildingName = false,
            isAddressBlockHouseNumber = false,
            isAddressStreetName = false,
            isDescription = false,
            isHyperlink = false,
            isCoordinates = false;


    public XMLSaxHandler() {
        details = new ArrayList<>();
    }

    public ArrayList<MapDetail> getDetails() {
        return details;
    }

    /** Gets be called on opening tags like:
     * <tag>
     * Can provide attribute(s), when xml was like:
     * <tag attribute="attributeValue">*/
    public void startElement(String namespaceURI, String localName, String qName,
                             Attributes attributes) throws SAXException {
        str = new StringBuilder();
        isName = false;
        isAddressPostalCode = false;
        isAddressUnitNumber = false;
        isAddressBuildingName = false;
        isAddressBlockHouseNumber = false;
        isAddressStreetName = false;
        isDescription = false;
        isHyperlink = false;
        isCoordinates = false;

        if (qName.equalsIgnoreCase("Placemark")) {
            detail = new MapDetail();
        } else if (qName.equalsIgnoreCase("SimpleData")) {
            if (attributes.getValue("name").equalsIgnoreCase("NAME"))
                isName = true;
            else if (attributes.getValue("name").equalsIgnoreCase("ADDRESSPOSTALCODE"))
                isAddressPostalCode = true;
            else if (attributes.getValue("name").equalsIgnoreCase("ADDRESSUNITNUMBER"))
                isAddressUnitNumber = true;
            else if (attributes.getValue("name").equalsIgnoreCase("ADDRESSBUILDINGNAME"))
                isAddressBuildingName = true;
            else if (attributes.getValue("name").equalsIgnoreCase("ADDRESSBLOCKHOUSENUMBER"))
                isAddressBlockHouseNumber = true;
            else if (attributes.getValue("name").equalsIgnoreCase("ADDRESSSTREETNAME"))
                isAddressStreetName = true;
            else if (attributes.getValue("name").equalsIgnoreCase("DESCRIPTION"))
                isDescription = true;
            else if (attributes.getValue("name").equalsIgnoreCase("HYPERLINK"))
                isHyperlink = true;
        }
        else if (qName.equalsIgnoreCase("coordinates"))
            isCoordinates = true;
    }

    /** Gets be called on the following structure:
     * <tag>characters</tag> */
    public void characters(char[] ch, int start, int length) {
        if (str != null) {
            for (int i=start; i<start+length; i++) {
                str.append(ch[i]);
            }
        }
    }

    /** Gets be called on closing tags like:
     * </tag> */
    public void endElement(String namespaceURI, String localName, String qName) {
        if (qName.equalsIgnoreCase("Placemark"))
            details.add(detail);
        else if (qName.equalsIgnoreCase("SimpleData"))
        {
            try {
                if (isName) {
                    int index1 = str.indexOf("(");
                    int index2 = str.indexOf(")");

                    if (index1 == -1)
                        detail.setName(str.toString());
                    else
                    {
                        detail.setName(str.substring(index1+1, index2));
                    }
                }

                else if (isAddressPostalCode)
                    detail.setAddressPostalCode(str.toString());

                else if (isAddressUnitNumber)
                    detail.setAddressUnitNumber(str.toString());

                else if (isAddressBuildingName)
                    detail.setAddressBuildingName(str.toString());

                else if (isAddressBlockHouseNumber)
                    detail.setAddressBlockHouseNumber(str.toString());

                else if (isAddressStreetName)
                    detail.setAddressStreetName(str.toString());

                else if (isDescription)
                    detail.setDescription(str.toString());

                else if (isHyperlink)
                    detail.setHyperlink(str.toString());

            } catch (NumberFormatException e)
            {
                e.printStackTrace();
            }
        }
        else if (qName.equalsIgnoreCase("coordinates"))
        {
            if (isCoordinates) {
                detail.setCoordinates(str.toString());
            }
        }
    }
}
