/*
 * Copyright 2018 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta.examples;

import ch.addere.jbauta.BautaFactory;
import ch.addere.jbauta.BautaV6;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPv6 {

  public static void main(String[] args) throws UnknownHostException {
    InetAddress publicAddress = InetAddress.getByName("2001:DB8::42");
    InetAddress privateAddress = InetAddress.getByName("::1");
    InetAddress fullLengthAddress = InetAddress.getByName("2001:DB8::0053:00FF:FEAA:BBCC");
    InetAddress result;

    BautaFactory factory = new BautaFactory();

    /*
     * 1. Default Bitmask
     */
    BautaV6 btaDefault = factory.createIPv6();

    /* Mask any address type */
    result = btaDefault.maskAny(publicAddress);
    System.out.println("\n" + result); // Output: /2001:db8:0:0:0:0:0:0

    result = btaDefault.maskAny(privateAddress);
    System.out.println(result); // Output: /0:0:0:0:0:0:0:0

    /* Mask only public address types */
    result = btaDefault.maskPublicRoutableOnly(publicAddress);
    System.out.println(result); // Output: /2001:db8:0:0:0:0:0:0

    result = btaDefault.maskPublicRoutableOnly(privateAddress);
    System.out.println(result); // Output: /0:0:0:0:0:0:0:1

    /*
     * 2. Custom Bitmask
     */
    InetAddress newBitmask = InetAddress.getByName("FFFF::");

    BautaV6 btaIPBitmask = factory.createIPv6(newBitmask);

    /* Mask any address type */
    result = btaIPBitmask.maskAny(publicAddress);
    System.out.println("\n" + result); // Output: /2001:0:0:0:0:0:0:0

    result = btaIPBitmask.maskAny(privateAddress);
    System.out.println(result); // Output: /0:0:0:0:0:0:0:0

    /* Mask only public address types */
    result = btaIPBitmask.maskPublicRoutableOnly(publicAddress);
    System.out.println(result); // Output: /2001:0:0:0:0:0:0:0

    result = btaIPBitmask.maskPublicRoutableOnly(privateAddress);
    System.out.println(result); // Output: /0:0:0:0:0:0:0:1

    /*
     * 3. Slash Bitmask
     */
    BautaV6 btaIPSlashBitmask = factory.createIPv6(16);

    /* Mask any address type */
    result = btaIPSlashBitmask.maskAny(publicAddress);
    System.out.println("\n" + result); // Output: /2001:0:0:0:0:0:0:0

    result = btaIPSlashBitmask.maskAny(privateAddress);
    System.out.println(result); // Output: /0:0:0:0:0:0:0:0

    /* Mask only public address types */
    result = btaIPSlashBitmask.maskPublicRoutableOnly(publicAddress);
    System.out.println(result); // Output: /2001:0:0:0:0:0:0:0

    result = btaIPSlashBitmask.maskPublicRoutableOnly(privateAddress);
    System.out.println(result); // Output: /0:0:0:0:0:0:0:1

    /*
     * 4. Only keep MAC from IP version 6 address
     */
    InetAddress keepMACBitmask = InetAddress.getByName("0:0:0:0:FFFF:FF00:00FF:FFFF");

    BautaV6 btaV6Advanced1 = factory.createIPv6(keepMACBitmask);

    result = btaV6Advanced1.maskAny(fullLengthAddress);
    System.out.println("\n" + result); // Output: /0:0:0:0:53:0:aa:bbcc

    /*
     * 5. Remove only MAC from IP version 6 address
     */
    InetAddress removeMACBitmask = InetAddress.getByName("FFFF:FFFF:FFFF:FFFF:0:FF:FF00:0");

    BautaV6 btaV6Advanced2 = factory.createIPv6(removeMACBitmask);

    result = btaV6Advanced2.maskAny(fullLengthAddress);
    System.out.println("\n" + result); // Output: /2001:db8:0:0:0:ff:fe00:0

  }

}
