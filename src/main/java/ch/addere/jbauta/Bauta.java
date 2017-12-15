/*
 * Copyright 2017 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;

/**
 * This utility class anonymises Internet Protocol (IP) address.
 *
 * An anonymised IP address hides the origin address and there for lets no one make conclusions of the original address and/or user.
 *
 * @author Pascal K. (ryru@addere.ch)
 */
public class Bauta {

  /**
   * Default IPv4 address mask
   * 255.255.240.0 or CIDR notation /20
   */
  private static final byte[] IPV4_MASK = {(byte) 255, (byte) 255, (byte) 240, (byte) 0};

  /**
   * Default IPv6 address mask
   * FF:FF:FF:FF:: or CIDR notation /32
   */
  private static final byte[] IPV6_MASK = {
      (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
  };

  /**
   * Array size of an IP version 4 address
   */
  private static final int IPV4_ARRAY_LENGTH = 4;

  /**
   * Array size of an IP version 6 address
   */
  private static final int IPV6_ARRAY_LENGTH = 16;

  private InetAddress ipv4Mask;
  private InetAddress ipv6Mask;
  private boolean maskNonRoutableAddress;

  /**
   * Initialise a new Bauta instance.
   */
  Bauta() {
    try {
      ipv4Mask = InetAddress.getByAddress(IPV4_MASK);
      ipv6Mask = InetAddress.getByAddress(IPV6_MASK);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    maskNonRoutableAddress = false;
  }


  /**
   * Masquerade Internet Protocol addresses.
   *
   * By default the last 12 bits (IPv4) or the last 96 bits (IPv6) will be truncated.
   *
   * @param address Internet Protocol (IP) address in version 4 or 6
   * @return Masqueraded IP address
   * @throws UnknownHostException in case address input is neither a valid IP version 4 nor 6
   */
  public InetAddress mask(final InetAddress address) throws UnknownHostException {
    if (address == null) {
      throw new InvalidParameterException();
    }

    final boolean isNonRoutableAddress =
        address.isAnyLocalAddress() ||
            address.isLoopbackAddress() ||
            address.isLinkLocalAddress() ||
            address.isSiteLocalAddress();

    if (isNonRoutableAddress && !maskNonRoutableAddress) {
      return address;
    } else {
      return masqueradeAddress(address);
    }
  }

  /**
   * Configure this instance to mask non routable address.
   *
   * By default only public routable IP addresses will be masked. This switch advises this instance of JBauta to mask private addresses as well.
   */
  public void maskNonRoutableAddress() {
    this.maskNonRoutableAddress = true;
  }

  /**
   * Overwrite default IPv4 bitmask.
   *
   * By default the last 12 bit are set to 0. This method allows overwriting. For example the IP 0.0.0.0 will set the input ip to 0.0.0.0 (all bit unset) where as the IP 255.255.255.255 (all bits set) will not change the input IP.
   *
   * @param mask IP version 4 bitmask
   */
  public void setIPv4Mask(final Inet4Address mask) {
    if (mask == null) {
      throw new InvalidParameterException();
    }

    ipv4Mask = mask;
  }

  /**
   * Overwrite default IPv6 bitmask.
   *
   * By default the last 96 bit are set to 0. This method allows overwriting. For example the IP :: will set the input ip to :: (all bit unset) where as the IP ff:ff:ff:ff:ff:ff:ff:ff (all bits set) will not change the input IP.
   *
   * @param mask IP version 6 bitmask
   */
  public void setIPv6Mask(final Inet6Address mask) {
    if (mask == null) {
      throw new InvalidParameterException();
    }

    ipv6Mask = mask;
  }


  private InetAddress masqueradeAddress(final InetAddress address) throws UnknownHostException {
    InetAddress mask;
    byte[] maskedBytes;

    final boolean isIPv4Address = address.getAddress().length == IPV4_ARRAY_LENGTH;
    final boolean isIPv6Address = address.getAddress().length == IPV6_ARRAY_LENGTH;

    if (isIPv4Address) {
      mask = ipv4Mask;
      maskedBytes = new byte[IPV4_ARRAY_LENGTH];
    } else if (isIPv6Address) {
      mask = ipv6Mask;
      maskedBytes = new byte[IPV6_ARRAY_LENGTH];
    } else {
      throw new UnknownHostException();
    }

    for (int i = 0; i < maskedBytes.length; i++) {
      maskedBytes[i] = (byte) (address.getAddress()[i] & mask.getAddress()[i]);
    }

    return InetAddress.getByAddress(maskedBytes);
  }
}
