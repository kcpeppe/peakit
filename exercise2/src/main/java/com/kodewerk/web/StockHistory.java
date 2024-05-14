package com.kodewerk.web;

import com.kodewerk.db.*;
import com.kodewerk.stock.*;

import  jakarta.servlet.http.HttpServletRequest;
import  jakarta.servlet.http.HttpServletResponse;
import  jakarta.servlet.http.HttpSession;

import java.io.OutputStream;
import java.io.IOException;
import java.util.Iterator;

public class StockHistory extends HistoryServlet {

    private ClosingPriceDataSource ds;

    public void init() {
        try {
            String dataSource = StockProperties.getDataSource();
            Class<? extends ClosingPriceDataSource> clazz =
                    Class.forName( dataSource, true, Thread.currentThread().getContextClassLoader())
                            .asSubclass(ClosingPriceDataSource.class);
            this.ds = clazz.getConstructor().newInstance();
        } catch (Exception e) {
            getServletContext().log("An exception occurred", e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ClosingPriceList priceList = null;
        response.setContentType("text/html");
        String log = null;

        String ticker = request.getParameter("ticker");
        if ( ticker != null)
            try {
                HttpSession session = request.getSession();
                CrumbTrail trail;

                if ( session.isNew())
                    session.setAttribute( "crumbs", new CrumbTrail());

                trail = (CrumbTrail)session.getAttribute("crumbs");

                String action = request.getParameter( "history");
                if ( "history".equals(action) || "analysis".equals(action)) {
                    try {
                        priceList = trail.getCrumb( ticker);
                    } catch(StockNotCachedException snce) {
                        log = " loading ";
                        priceList = ds.load( ticker);
                        trail.addCrumb( priceList);
                    }
                } else {
                    action = request.getParameter("lastClose");
                    if ( "last close".equals(action)) {
                        ClosingPrice price = ds.getLatestClosingPrice( ticker);
                        priceList = new ClosingPriceList( ticker);
                        priceList.addClosingPrice( price);
                    }
                }
            } catch (ClosingPriceDataSourceException e) {
                getServletContext().log("An exception occurred", e);
                priceList = null;
            }
        this.writeHTMLDocument(response.getOutputStream(), priceList);
    }


    /**
     * We are going to perform the same operations for POST requests
     * as for GET methods, so this method just sends the request to
     * the doGet method.
     */

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public void writeHTMLDocument( OutputStream stream, ClosingPriceList priceList) throws IOException {
        this.write( stream, super.getHeader());
        this.write( stream, "<body><H1>Stock History</H1>");
        constructForm( stream);
        constructResultsTable(stream,priceList);
        this.write( stream, "</body></html>");
    }

    private void constructForm( OutputStream stream) throws IOException {
        this.write( stream, "<form action=\"http://localhost:8080/lab/stock\" method=\"post\">");
        this.constructDropdownList( stream);
        this.write( stream, "<input type=\"submit\" name=\"history\" value=\"history\">");
        this.write( stream, "<input type=\"submit\" name=\"lastClose\" value=\"last close\">");
        this.write( stream, "<input type=\"submit\" name=\"analysis\" value=\"analysis\">");
        this.write( stream, "</form>");
    }

    private void constructDropdownList( OutputStream stream) throws IOException {

        Iterator tickerSymbols = new TickerList().iterator();
        this.write( stream, "<select name=\"ticker\">");
        while ( tickerSymbols.hasNext()) {
            String tickerSymbol = (String)tickerSymbols.next();
            this.write( stream, "<option value=\"");
            this.write( stream, tickerSymbol);
            this.write( stream, "\">");
            this.write( stream, tickerSymbol);
            this.write( stream, "</option>");
        }
        this.write( stream,"</select>");
    }


    public void constructResultsTable( OutputStream stream, ClosingPriceList priceList) throws IOException {
        if ( priceList == null) return;
        this.write( stream, "<H2>" + priceList.getTicker() + "</H2>");
        this.write( stream, "<br><table border=\"1\">");
        this.write( stream, "<TR><TH>Date</TH><TH>Open</TH><TH>High</TH><TH>Low</TH><TH>Close</TH><TH>Volume</TH><TH>Adjusted Close</TH></TR>");
        Iterator prices = priceList.iterator();
        boolean even = true;
        while ( prices.hasNext()) {
            ClosingPrice price = (ClosingPrice)prices.next();
            this.write( stream, "<tr class=\"");
            if ( even) 
                this.write( stream, "true");
            else
                this.write( stream, "false");
            this.write( stream, "\"><td>");
            this.write( stream, price.getDate());
            this.write( stream, "</td><td>");
            this.write( stream, price.getOpen());
            this.write( stream, "</td><td>");
            this.write( stream, price.getHigh());
            this.write( stream,"</td><td>");
            this.write( stream, price.getLow());
            this.write( stream, "</td><td>");
            this.write( stream, price.getClose());
            this.write( stream, "</td><td>");
            this.write( stream, price.getVolume());
            this.write( stream, "</td><td>");
            this.write( stream, price.getAdjustedClose());
            this.write( stream, "</td></tr>");
            even = ! even;
        }
        this.write( stream, "</table>");
    }

    private void write( OutputStream stream, String text) throws IOException {
        stream.write(text.getBytes());
    }
}
