/*
 * Copyright 2017 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import org.jetbrains.annotations.NotNull;

/**
 * Bauta IPv4 context.
 *
 */
public class BautaV4 extends Bauta {

  /**
   * Create BautaV4 instance.
   *
   * @param mask IPv4 address to masquerade
   */
  public BautaV4(final Inet4Address mask) {
    super(mask);
  }

  /**
   * Masquerade any IPv4 address.
   *
   * This method masquerades any given IPv4 address with the bitmask set in this instance.
   *
   * @param addressToMask IPv4 address to mask
   * @return Anonymised IPv4 instance
   * @throws UnknownHostException If provided IPv4 is not valid
   */
  public Inet4Address maskAny(@NotNull final Inet4Address addressToMask)
      throws UnknownHostException {

    return (Inet4Address) maskAnyIPAddress(addressToMask);
  }

  /**
   * Masquerade public routable IPv4 address only.
   *
   * This method masquerades public routable IPv4 address only with the bitmask set in this instance. All private IP addresses will not be anonymised (see RFC1918).
   *
   * @param addressToMask IPv4 address to mask
   * @return Anonymised IPv4 instance
   * @throws UnknownHostException If provided IPv4 is not valid
   */
  public Inet4Address maskPublicRoutableOnly(@NotNull final Inet4Address addressToMask)
      throws UnknownHostException {

    return (Inet4Address) maskPublicRoutableIPAddressOnly(addressToMask);
  }

}
