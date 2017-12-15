# JBauta
Easy to use and privacy by default Internet Protocol (IP) masking library.

Bauta is a straight forward Internet Protocol (IP) address anonymisation library written in Java. The API takes an IP version 4 or 6 and hides it by transforming the address in a big pool of valid addresses. Probably the best real world analogy is the anonymity level a big crowed of people, e.g. a carnival, gives to a single individual among.

Instead of just setting user IP addresses to zero the library just removes the last part of an address. This gives the ability to perform some sort of statistics will providing privacy for users.

This software is inspired by the [AnonIP](https://github.com/DigitaleGesellschaft/Anonip) project of the Digital Society Switzerland.


## Example Usage

For example the IP address `203.0.113.1` shall be masked, the most simple usage is:
```
  InetAddress ipv4 = InetAddress.getByName("203.0.113.1");

  Bauta bta = new Bauta();
  ipv4 = bta.mask(ipv4);

  System.out.println(ipv4);
```

Output is `/203.0.112.0`


If you would like to overwrite IP version 6 masking to just mask a MAC address `00-53-00-AA-BB-CC` part in a IP version 6 use:
```
  InetAddress ipv6 = InetAddress.getByName("2001:DB8::0053:00FF:FEAA:BBCC");
  InetAddress mask = InetAddress.getByName("ffff:ffff:ffff:ffff:0000:00FF:FF00:0000");

  Bauta bta = new Bauta();
  bta.setIPv6Mask((Inet6Address) mask);
  ipv6 = bta.mask(ipv6);

  System.out.println(ipv6);
```

Output is `/2001:db8:0:0:0:ff:fe00:0`.
By default an IPv6 interface identifier is build based of the MAC address split in two half and combined with `FFFE` in the middle.

## Features

Bauta is able to handle IP version 4 and 6 addresses.
  * Privacy By Default - IPv4 addresses are masked by removing the last 12 bits, IPv6 by removing the last 96 bits.
  * Easy to use - The API is small and really easy to use. For example the same masking Interface is able to handle IP version 4 and 6.
  * Formatting - Since the API uses InetAddress it supports all common IP version 4 and 6 notation. Including IPv6 short notation form `(::)` and IPv4-ending `(::192.186.2.1)`.
  * Non Routable Addresses - By default tie API does not mask private or public non routable addresses. This, however, can be overwritten `maskNonRoutableAddress()`.                                                                                                                                               `.
  * Masking - It is possible to overwrite default IPv4 and IPv6 masking

## How it works
The approach is rather simple but effective. Basically a given IP address will be bitwise ANDed with a bitmask, the mask. By default the mask is set to 255.255.240.0 for IP version 4 and ff:ff:: for IP version 6 addresses. This cuts of the last bits of the input IP address, which hides the origin.

## License
This project is released under the terms of the 2-Clause BSD License. See license.txt.

## Naming
The Bauta is a mask which was important at the Carnival of Venice. In 18th century, the Bauta provided, apart from the carnival, the city's nobility a standard way of anonymity.

![Bauta Mask](https://upload.wikimedia.org/wikipedia/commons/thumb/e/e6/Maschere_veneziane_-_Ba%C3%B9ta.jpg/189px-Maschere_veneziane_-_Ba%C3%B9ta.jpg)

