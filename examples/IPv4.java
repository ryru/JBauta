/*
 * Copyright 2018 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta.examples;

import ch.addere.jbauta.BautaFactory;
import ch.addere.jbauta.BautaV4;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Example snippets to illustrate functionality of Bauta
 */
public class IPv4 {

  public static void main(String[] args) throws UnknownHostException {

    InetAddress publicAddress = InetAddress.getByName("203.0.113.42");
    InetAddress privateAddress = InetAddress.getByName("192.168.2.42");
    InetAddress result;

    BautaFactory factory = new BautaFactory();

    /*
     * 1. Default Bitmask
     */
    BautaV4 btaDefault = factory.createIPv4();

    /* Mask any address type */
    result = btaDefault.maskAny(publicAddress);
    System.out.println("\n" + result); // Output: /203.0.112.0

    result = btaDefault.maskAny(privateAddress);
    System.out.println(result); // Output: /192.168.0.0

    /* Mask only public address types */
    result = btaDefault.maskPublicRoutableOnly(publicAddress);
    System.out.println(result); // Output: /203.0.112.0

    result = btaDefault.maskPublicRoutableOnly(privateAddress);
    System.out.println(result); // Output: /192.168.2.42

    /*
     * 2. Custom Bitmask
     */
    InetAddress newBitmask = InetAddress.getByName("255.192.0.0");

    BautaV4 btaIPBitmask = factory.createIPv4(newBitmask);

    /* Mask any address type */
    result = btaIPBitmask.maskAny(publicAddress);
    System.out.println("\n" + result); // Output: /203.0.0.0

    result = btaIPBitmask.maskAny(privateAddress);
    System.out.println(result); // Output: /192.128.0.0

    /* Mask only public address types */
    result = btaIPBitmask.maskPublicRoutableOnly(publicAddress);
    System.out.println(result); // Output: /203.0.0.0

    result = btaIPBitmask.maskPublicRoutableOnly(privateAddress);
    System.out.println(result); // Output: /192.168.2.42

    /*
     * 3. Slash Bitmask
     */
    BautaV4 btaIPSlashBitmask = factory.createIPv4(16);

    /* Mask any address type */
    result = btaIPSlashBitmask.maskAny(publicAddress);
    System.out.println("\n" + result); // Output: /203.0.0.0

    result = btaIPSlashBitmask.maskAny(privateAddress);
    System.out.println(result); // Output: /192.128.0.0

    /* Mask only public address types */
    result = btaIPSlashBitmask.maskPublicRoutableOnly(publicAddress);
    System.out.println(result); // Output: /203.0.0.0

    result = btaIPSlashBitmask.maskPublicRoutableOnly(privateAddress);
    System.out.println(result); // Output: /192.168.2.42
  }
}
