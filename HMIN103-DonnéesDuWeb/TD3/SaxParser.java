/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 2000-2002 The Apache Software Foundation.  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Crimson" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 1999, Sun Microsystems, Inc., 
 * http://www.sun.com.  For more information on the Apache Software 
 * Foundation, please see <http://www.apache.org/>.
 */

// JAXP packages
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

/**
 * Program to be modified to implement the begin/end schema encoding. 
 * 
 * The present code is drawn from SAXLocalNameCount.java (2002-04-18 by Edwin Goei)
 */

public class SaxParser extends DefaultHandler {
    /** Constants used for JAXP 1.2 */
    static final String JAXP_SCHEMA_LANGUAGE =
        "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String W3C_XML_SCHEMA =
        "http://www.w3.org/2001/XMLSchema";
    static final String JAXP_SCHEMA_SOURCE =
        "http://java.sun.com/xml/jaxp/properties/schemaSource";

    /** A Hashtable with tag names as keys and Integers as values */
    private Hashtable tags;
    private String [] tabDB = new String [3];
    private String [] tabBatiment = new String [3];
    private String [] tabEtage = new String [3];
    private String [] tabDescription = new String [3];
    private String [] tabBureau = new String [3];
    private String [] tabCode = new String [3];
    private String [] tabPersonne = new String [3];
    private String [] tabSalle = new String [3];
    private String [] tabNombrePlaces = new String [3];              
    private boolean bdtexte = false;
    private String [] tabDescriptionTexte = new String [3];  
    private boolean bctexte = false;
    private String [] tabCodeTexte = new String [3];  
    private boolean bptexte = false;
    private String [] tabPersoTexte = new String [3];  
    private boolean bnptexte = false;
    private String [] tabNbPlacesTexte = new String [3];  

    // Parser calls this once at the beginning of a document
    public void startDocument() throws SAXException {
        tags = new Hashtable();
    }

    // Parser calls this for each opening of an element in a document
    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts)
	throws SAXException
    {
        System.out.println("starting an element "+localName);
        /*if(qName.equalsIgnoreCase("db")){
            System.out.println("INSERT INTO NODE (begin, end, par, tag, nodtyp) VALUES (" + atts.getValue("begin") + ", " + atts.getValue("end"); + ", " + atts.getValue("par") + ", '" + localName + "', Texte);");
        }
        if(qName.equalsIgnoreCase("batiment")){
            System.out.println("INSERT INTO NODE (begin, end, par, tag, nodtyp) VALUES (" + atts.getValue("begin") + ", " + atts.getValue("end"); + ", " + atts.getValue("par") + ", '" + localName + "', Texte);");
        }
        if(qName.equalsIgnoreCase("etage")){
            System.out.println("INSERT INTO NODE (begin, end, par, tag, nodtyp) VALUES (" + atts.getValue("begin") + ", " + atts.getValue("end"); + ", " + atts.getValue("par") + ", '" + localName + "', Texte);");
        }
        if(qName.equalsIgnoreCase("description")){
            System.out.println("INSERT INTO NODE (begin, end, par, tag, nodtyp) VALUES (" + atts.getValue("begin") + ", " + atts.getValue("end"); + ", " + atts.getValue("par") + ", '" + localName + "', Texte);");
        }
        if(qName.equalsIgnoreCase("bureau")){
            System.out.println("INSERT INTO NODE (begin, end, par, tag, nodtyp) VALUES (" + atts.getValue("begin") + ", " + atts.getValue("end"); + ", " + atts.getValue("par") + ", '" + localName + "', Texte);");
        }
        if(qName.equalsIgnoreCase("code")){
            System.out.println("INSERT INTO NODE (begin, end, par, tag, nodtyp) VALUES (" + atts.getValue("begin") + ", " + atts.getValue("end"); + ", " + atts.getValue("par") + ", '" + localName + "', Texte);");
        }*/

        if(qName.equalsIgnoreCase("dtexte")){
            bdtexte = true;
            tabDescriptionTexte[0] = atts.getValue("begin");
            tabDescriptionTexte[1] = atts.getValue("end");
            tabDescriptionTexte[2] = atts.getValue("par");
        } 
        else if(qName.equalsIgnoreCase("ctexte")){
            bctexte = true;
            tabCodeTexte[0] = atts.getValue("begin");
            tabCodeTexte[1] = atts.getValue("end");
            tabCodeTexte[2] = atts.getValue("par");
        } 
        else if(qName.equalsIgnoreCase("ptexte")){
            bptexte = true;
            tabPersoTexte[0] = atts.getValue("begin");
            tabPersoTexte[1] = atts.getValue("end");
            tabPersoTexte[2] = atts.getValue("par");
        } 
        else if(qName.equalsIgnoreCase("nptexte")){
            bnptexte = true;
            tabNbPlacesTexte[0] = atts.getValue("begin");
            tabNbPlacesTexte[1] = atts.getValue("end");
            tabNbPlacesTexte[2] = atts.getValue("par");
        } else{
            System.out.println("INSERT INTO NODE (begin, end, par, tag, nodtyp) VALUES (" + atts.getValue("begin") + ", " + atts.getValue("end") + ", " + atts.getValue("par") + ", '" + localName + "', Element);");
        }
    }
    
    // Parser calls this for each end of an element in a document
    public void endElement(String namespaceURI, String localName,
                             String qName)
	throws SAXException
    {
    	    
    }
    
    // Parser calls this once after parsing a document
    public void endDocument() throws SAXException {
            System.out.println("Bye bye");
    }

    // Parser calls after parsing a text node
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        if(bptexte){
            String nodeTexte = new String(ch, start, length);
            System.out.println("INSERT INTO NODE (begin, end, par, tag, nodtyp) VALUES (" + tabPersoTexte[0] + ", " + tabPersoTexte[1] + ", " + tabPersoTexte[2] + ", '" + nodeTexte.trim() + "', Texte);");
            bptexte = false;
        }else if (bdtexte){
            String nodeTexte = new String(ch, start, length);
            System.out.println("INSERT INTO NODE (begin, end, par, tag, nodtyp) VALUES (" + tabDescriptionTexte[0] + ", " + tabDescriptionTexte[1] + ", " + tabDescriptionTexte[2] + ", '" + nodeTexte.trim() + "', Texte);");
            bdtexte = false;
        }else if (bctexte){
            String nodeTexte = new String(ch, start, length);
            System.out.println("INSERT INTO NODE (begin, end, par, tag, nodtyp) VALUES (" + tabCodeTexte[0] + ", " + tabCodeTexte[1] + ", " + tabCodeTexte[2] + ", '" + nodeTexte.trim() + "', Texte);");
            bctexte = false;
        }else if (bnptexte){
            String nodeTexte = new String(ch, start, length);
            System.out.println("INSERT INTO NODE (begin, end, par, tag, nodtyp) VALUES (" + tabNbPlacesTexte[0] + ", " + tabNbPlacesTexte[1] + ", " + tabNbPlacesTexte[2] + ", '" + nodeTexte.trim() + "', Texte);");
            bnptexte = false;
        }
    }

	
	
    
    /**
     * Convert from a filename to a file URL.
     */
    private static String convertToFileURL(String filename) {
        // On JDK 1.2 and later, simplify this to:
        // "path = file.toURL().toString()".
        String path = new File(filename).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }

    private static void usage() {
        System.err.println("Usage: SAXLocalNameCount [-options] <file.xml>");
        System.exit(1);
    }

    static public void main(String[] args) throws Exception {
        String filename = "batiment-1.xml";
        
                if (args.length < 1) {
                    usage();
                }
                else 
                	filename = args[0];

                

        // Create a JAXP SAXParserFactory and configure it
        SAXParserFactory spf = SAXParserFactory.newInstance();

        // Set namespaceAware to true to get a parser that corresponds to
        // the default SAX2 namespace feature setting.  This is necessary
        // because the default value from JAXP 1.0 was defined to be false.
        spf.setNamespaceAware(true);

        // Create a JAXP SAXParser
        SAXParser saxParser = spf.newSAXParser();


        // Get the encapsulated SAX XMLReader
        XMLReader xmlReader = saxParser.getXMLReader();

        // Set the ContentHandler of the XMLReader
        xmlReader.setContentHandler(new SaxParser());

        // Set an ErrorHandler before parsing
        xmlReader.setErrorHandler(new MyErrorHandler(System.err));

        // Tell the XMLReader to parse the XML document
        xmlReader.parse(convertToFileURL(filename));
    }

    // Error handler to report errors and warnings
    private static class MyErrorHandler implements ErrorHandler {
        /** Error handler output goes here */
        private PrintStream out;

        MyErrorHandler(PrintStream out) {
            this.out = out;
        }

        /**
         * Returns a string describing parse exception details
         */
        private String getParseExceptionInfo(SAXParseException spe) {
            String systemId = spe.getSystemId();
            if (systemId == null) {
                systemId = "null";
            }
            String info = "URI=" + systemId +
                " Line=" + spe.getLineNumber() +
                ": " + spe.getMessage();
            return info;
        }

        // The following methods are standard SAX ErrorHandler methods.
        // See SAX documentation for more info.

        public void warning(SAXParseException spe) throws SAXException {
            out.println("Warning: " + getParseExceptionInfo(spe));
        }
        
        public void error(SAXParseException spe) throws SAXException {
            String message = "Error: " + getParseExceptionInfo(spe);
            throw new SAXException(message);
        }

        public void fatalError(SAXParseException spe) throws SAXException {
            String message = "Fatal Error: " + getParseExceptionInfo(spe);
            throw new SAXException(message);
        }
    }
}
