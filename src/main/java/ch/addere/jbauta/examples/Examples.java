/*
 * Copyright 2018 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta.examples;

import ch.addere.jbauta.BautaFactory;
import ch.addere.jbauta.BautaV4;
import ch.addere.jbauta.BautaV6;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Example snippets.
 *
 * This snippets are also used in the readme.md documentation.
 *
 * @author Pascal K. (ryru@addere.ch)
 * @version $Id$
 */
public class Examples {

  public static void main(String[] args) throws UnknownHostException {

    BautaFactory factory = new BautaFactory();
    BautaV4 btaV4 = factory.createIPv4();

    /*
     * 1. Example IP version 4
     */
    System.out.println();
    System.out.println("1. Example");

    Inet4Address ipV4Public = (Inet4Address) InetAddress.getByName("203.0.113.42");

    Inet4Address maskedV4 = btaV4.maskAny(ipV4Public);

    System.out.println("Input : " + ipV4Public);
    System.out.println("Output: " + maskedV4);


    /*
     * 2. Example IP version 4
     */
    System.out.println();
    System.out.println("2. Example");

    Inet4Address ipV4Private = (Inet4Address) InetAddress.getByName("192.168.2.42");

    Inet4Address notMaskedV4 = btaV4.maskPublicRoutableOnly(ipV4Private);

    System.out.println("Input : " + ipV4Private);
    System.out.println("Output: " + notMaskedV4);

    BautaV6 btaV6Default = factory.createIPv6();
    /*
     * 3. Example IP version 6
     */
    System.out.println();
    System.out.println("3. Example");

    Inet6Address ipV6Public = (Inet6Address) InetAddress.getByName("2001:DB8::42");

    Inet6Address ipV6Default = btaV6Default.maskAny(ipV6Public);

    System.out.println("Input : " + ipV6Public);
    System.out.println("Output: " + ipV6Default);

    /*
     * 4. Example IP version 6
     */
    System.out.println();
    System.out.println("4. Example");

    Inet6Address ipV6Mask = (Inet6Address) InetAddress.getByName("0:0:0:0:FFFF:FF00:00FF:FFFF");
    Inet6Address ipV6MAC = (Inet6Address) InetAddress.getByName("2001:DB8::0053:00FF:FEAA:BBCC");

    BautaV6 btaV6Advanced = factory.createIPv6(ipV6Mask);

    Inet6Address ipV6MACOnly = btaV6Advanced.maskAny(ipV6MAC);

    System.out.println("Input : " + ipV6MAC);
    System.out.println("Output: " + ipV6MACOnly);

  }

}
