/*
 * Copyright 2018 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.jetbrains.annotations.NotNull;

/**
 * Bauta IPv6 context.
 *
 */
public class BautaV6 extends Bauta {

  private static final int IPV6_BYTE_COUNT = 16;

  /**
   * Create BautaV6 instance.
   *
   * @param mask IPv6 address to masquerade
   */
  public BautaV6(@NotNull final InetAddress mask) {
    super(mask);

    if (!isIPv6(mask)) {
      throw new IllegalArgumentException("Error: mask is not a valid IP version 6 address");
    }
  }

  /**
   * Masquerade any IPv6 address.
   *
   * This method masquerades any given IPv6 address with the bitmask set in this instance.
   *
   * @param addressToMask IPv6 address to mask
   * @return Anonymised IPv6 instance
   * @throws UnknownHostException If provided IPv6 is not valid
   */
  public InetAddress maskAny(@NotNull final InetAddress addressToMask)
      throws UnknownHostException {

    if (isIPv6(addressToMask)) {
      return maskAnyIPAddress(addressToMask);
    } else {
      return addressToMask;
    }
  }

  /**
   * Masquerade public routable IPv6 address only.
   *
   * This method masquerades public routable IPv6 address only with the bitmask set in this instance. All private IP addresses will not be anonymised (see RFC4291).
   *
   * @param addressToMask IPv6 address to mask
   * @return Anonymised IPv6 instance
   * @throws UnknownHostException If provided IPv6 is not valid
   */
  public InetAddress maskPublicRoutableOnly(@NotNull final InetAddress addressToMask)
      throws UnknownHostException {

    if (isIPv6(addressToMask)) {
      return maskPublicRoutableIPAddressOnly(addressToMask);
    } else {
      return addressToMask;
    }
  }

  private boolean isIPv6(final InetAddress address) {
    boolean isIPv6 = true;
    int addressByteCount = address.getAddress().length;

    if (addressByteCount != IPV6_BYTE_COUNT) {
      isIPv6 = false;
    }

    return isIPv6;
  }

}
