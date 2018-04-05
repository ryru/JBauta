# JBauta
Easy to use and privacy by default Internet Protocol (IP) masking library.

Bauta is a straight forward Internet Protocol (IP) address anonymisation library written in Java. The API takes an IP version 4 or 6 and hides it by transforming the address in a big pool of possible addresses. Probably the best real world analogy is the anonymity level a big crowed of people, e.g. a carnival, gives to a single individual among.

Instead of just setting the whole IP addresses to zero Bauta, by default, removes the last part of an address. This property gives anyone the ability to protect users privacy but still do some sort of statistical evaluation on country or even city part of any IP addresses.


This software is inspired by the [AnonIP](https://github.com/DigitaleGesellschaft/Anonip) project of the Digital Society Switzerland.

## Features

Bauta is able to anonymise IP version 4 and 6 addresses.
  * Privacy - By default IP version 4 addresses are masked by removing the last 12 bits, IP version 6 by removing the last 96 bits.
  * Easy - The API is small and easy to use. See Example Usage.
  * Formatting - Since the API uses `InetAddress` support for all IP version 4 and 6 styles are supported, including short notation form `(::)` and IP version 4 ending `(::192.186.2.1)`.
  * Control - Choose if you want to mask any address or only public routable addresses.
  * Masking - Custom IP version 4 or 6 bitmask are possible too

## Example Usage
In order to masquerade IP addresses you have to do three basic steps:

  1. Create a Bauta factory
  2. Create a Bauta context with default or custom masquerading bitmask
  3. Mask any IP version 4 or 6 address you like

All methods and functionality from IP version 4 works as well for IP version 6 and vice versa. Take also a look at the Unit Tests for further example code or at the code documentation.
  
For example the IP address `203.0.113.42` shall be masked, the most simple way to achieve this is:
```
  BautaFactory factory = new BautaFactory();
  Bauta bta = factory.createDefaultIPMask();
  
  InetAddress ipV4Address = InetAddress.getByName("203.0.113.42");
  
  InetAddress masqueradedAddress = bta.maskAny(ipV4Address);
  System.out.println("Input  : " + ipV4Address);
  System.out.println("Output : " + masqueradedAddress);
  
  /*
   * Output is
   */
  Input : /203.0.113.42
  Output: /203.0.112.0
```

The same Bauta context works for this IP version 6 `2001:DB8::42` as well:
```
  BautaFactory factory = new BautaFactory();
  Bauta bta = factory.createDefaultIPMask();
  
  InetAddress ipV6Address = InetAddress.getByName("2001:DB8::42");

  InetAddress masqueradedAddress = bta.maskAny(ipV6Address);
  System.out.println("Input  : " + ipV6Address);
  System.out.println("Output : " + masqueradedAddress);
  
  /*
   * Output is
   */
  Input : /2001:db8:0:0:0:0:0:42
  Output: /2001:db8:0:0:0:0:0:0
```

### Exclude local private IP addresses
If you like to keep local private IP addresses, for example in your troubleshooting log file, untouched you can simply use the method `maskPublicRoutableOnly()` as showen in this example:
```
  BautaFactory factory = new BautaFactory();
  Bauta bta = factory.createDefaultIPMask();
  
  InetAddress ipV6Address = InetAddress.getByName("192.168.2.42");
  
  InetAddress masqueradedAddress = bta.maskPublicRoutableOnly(ipV6Address);
  System.out.println("Input  : " + ipV6Address);
  System.out.println("Output : " + masqueradedAddress);
  
  /*
   * Output is
   */
  Input : /192.168.2.42
  Output: /192.168.2.42
```

### Use custom bitmasks instead of privacy by default masking
You can set custom bitmask either by using CIDR notation or by setting custom masking addresses. The second approach is more complex but is more powerful in order to achieve advanced goals.

#### CIDR style masking
By using the factory method `createCustomIPMask` you can use a CIDR style notation for custom bitmasking. For example the value `13` will set the first 13 bits to one and leaves the last 19 set to zero:
```
  BautaFactory factory = new BautaFactory();
  Bauta bta = factory.createCustomIPMask(13, 42);
  
  InetAddress ipV4Address = InetAddress.getByName("203.0.113.42");
  
  InetAddress masqueradedAddress = bta.maskPublicRoutableOnly(ipV4Address);
  System.out.println("Input  : " + ipV4Address);
  System.out.println("Output : " + masqueradedAddress);
  
  /*
   * Output is
   */
  Input : /203.0.113.42
  Output: /203.0.0.0
```
The value `42` in the above example is for IP version 6 addresses.

#### Address style masking
By using the factory method `createCustomIPMask` with `InetAddress` parameters you can specify an IP version 4 and 6 as bitmask. This gives you control over every single bit of an address.
```
  InetAddress ipv4Mask = InetAddress.getByName("255.255.0.0");
  InetAddress ipv6Mask = InetAddress.getByName("00FF:00FF::FF");
  
  BautaFactory factory = new BautaFactory();
  Bauta bta = factory.createCustomIPMask(ipv4Mask, ipv6Mask);
  
  InetAddress ipV6Address = InetAddress.getByName("2001:DB8::42");
  
  InetAddress masqueradedAddress = bta.maskPublicRoutableOnly(ipV6Address);
  System.out.println("Input  : " + ipV6Address);
  System.out.println("Output : " + masqueradedAddress);
    
  /*
   * Output is
   */
  Input : /2001:db8:0:0:0:0:0:42
  Output: /1:b8:0:0:0:0:0:42
```



## How Bauta works
The approach is rather simple but effective. A given IP address will be bitwise ANDed with a bit mask. By default the mask is set to 255.255.240.0 for IP version 4 and ffff:ffff:: for IP version 6 addresses. Cutting of the last bits of each address hides the original address but still allows network troubleshooting and statistical evaluation on a country or even city base.

## License
This project is released under the terms of the 2-Clause BSD License. See license.txt.

## Name of the Project
Bauta is a mask which was important at the Carnival of Venice. In 18th century a Bauta provided, apart from the carnival, the city's nobility a standard way of anonymity.

![Bauta Mask](https://upload.wikimedia.org/wikipedia/commons/thumb/e/e6/Maschere_veneziane_-_Ba%C3%B9ta.jpg/189px-Maschere_veneziane_-_Ba%C3%B9ta.jpg)

