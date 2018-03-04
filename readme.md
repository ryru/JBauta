# JBauta
Easy to use and privacy by default Internet Protocol (IP) masking library.

Bauta is a straight forward Internet Protocol (IP) address anonymisation library written in Java. The API takes an IP version 4 or 6 and hides it by transforming the address in a big pool of valid addresses. Probably the best real world analogy is the anonymity level a big crowed of people, e.g. a carnival, gives to a single individual among.

Instead of just setting the whole IP addresses to zero Bauta, by default, removes the last part of an address. This property gives anyone the ability to protect users privacy but still do some sort of statistical evaluation on country or even city part of any IP addresses.


This software is inspired by the [AnonIP](https://github.com/DigitaleGesellschaft/Anonip) project of the Digital Society Switzerland.

## Features

Bauta is able to anonymise IP version 4 and 6 addresses.
  * Privacy - By default IP version 4 addresses are masked by removing the last 12 bits, IP version 6 by removing the last 96 bits.
  * Easy - The API is small and easy to use. See Example Usage.
  * Formatting - Since the API uses `InetAddress` it supports all common IP version 4 and 6 notation. Including IPv6 short notation form `(::)` and IPv4-ending `(::192.186.2.1)`.
  * Control - Choose if you want to mask any address or only public routable addresses.
  * Masking - It is possible to overwrite default IPv4 and IPv6 masking

## Example Usage
All methods and functionality from IP version 4 works as well for IP version 6 and vice versa. Take also a look at the examples directory.

### IP version 4
First off a IP version 4 factory is needed:
```
  BautaFactory factory = new BautaFactory();
  BautaV4 btaV4 = factory.createIPv4();
```


For example the IP address `203.0.113.1` shall be masked, the most simple way to achieve this is:
```
  Inet4Address ipV4Public = (Inet4Address) InetAddress.getByName("203.0.113.42");
  
  Inet4Address maskedV4 = btaV4.maskAny(ipV4Public);
  
  System.out.println("Input : " + ipV4Public);
  System.out.println("Output: " + maskedV4);
  
  /*
   * Output is
   */
  Input : /203.0.113.42
  Output: /203.0.112.0
```

Keeping local IP addresses in the log file is possible by using `maskPublicRoutableOnly()`:
```
  Inet4Address ipV4Private = (Inet4Address) InetAddress.getByName("192.168.2.42");
  
  Inet4Address notMaskedV4 = btaV4.maskPublicRoutableOnly(ipV4Private);
  
  System.out.println("Input : " + ipV4Private);
  System.out.println("Output: " + notMaskedV4);
  
  /*
   * Output is
   */
  Input : /192.168.2.42
  Output: /192.168.2.42
```


### IP version 6
First off a IP version 6 factory is needed:
```
  BautaFactory factory = new BautaFactory();
  BautaV6 btaV6Default = factory.createIPv6();
```

For example the IP address `2001:DB8::42` shall be masked, the most simple way to achieve this is:
```
  Inet6Address ipV6Public = (Inet6Address) InetAddress.getByName("2001:DB8::42");
  
  Inet6Address ipV6Default = btaV6Default.maskAny(ipV6Public);
  
  System.out.println("Input : " + ipV6Public);
  System.out.println("Output: " + ipV6Default);
  
  /*
   * Output is
   */
  Input : /2001:db8:0:0:0:0:0:42
  Output: /2001:db8:0:0:0:0:0:0
```

A more advanced example scenario is the keep, for example, only the MAC address `00-53-00-AA-BB-CC` part of an IP version 6 address. To achieve this use this:
```
    Inet6Address ipV6Mask = (Inet6Address) InetAddress.getByName("0:0:0:0:FFFF:FF00:00FF:FFFF");
    Inet6Address ipV6MAC = (Inet6Address) InetAddress.getByName("2001:DB8::0053:00FF:FEAA:BBCC");
    
    BautaV6 btaV6Advanced = factory.createIPv6(ipV6Mask);
    
    Inet6Address ipV6MACOnly = btaV6Advanced.maskAny(ipV6MAC);
    
    System.out.println("Input : " + ipV6MAC);
    System.out.println("Output: " + ipV6MACOnly);
      
  /*
   * Output is
   */
  Input : /2001:db8:0:0:53:ff:feaa:bbcc
  Output: /0:0:0:0:53:0:aa:bbcc
```


## How Bauta works
The approach is rather simple but effective. A given IP address will be bitwise ANDed with a bit mask. By default the mask is set to 255.255.240.0 for IP version 4 and ffff:ffff:: for IP version 6 addresses. Cutting of the last bits of each address hides the original address but still allows statistical evaluation on a country or even city base.

## License
This project is released under the terms of the 2-Clause BSD License. See license.txt.

## Name of the Project
Bauta is a mask which was important at the Carnival of Venice. In 18th century a Bauta provided, apart from the carnival, the city's nobility a standard way of anonymity.

![Bauta Mask](https://upload.wikimedia.org/wikipedia/commons/thumb/e/e6/Maschere_veneziane_-_Ba%C3%B9ta.jpg/189px-Maschere_veneziane_-_Ba%C3%B9ta.jpg)

