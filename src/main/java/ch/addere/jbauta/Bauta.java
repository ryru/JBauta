/*
 * Copyright 2018 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * Masquerading logic for both IP version 4 and 6.
 */
public class Bauta {

  final private Masquerade calc;

  /**
   * Initialise a Bauta context by using the BautaFactory class.
   *
   * <p>Precondition: ipv4Mask is a valid IP version 4 address and not equals null</p>
   * <p>Precondition: ipv6MAsk is a valid IP version 6 address and not equals null</p>
   *
   * @param ipv4Mask A valid IP version 4 address as masking reference
   * @param ipv6Mask A valid IP version 6 address as masking reference
   */
  protected Bauta(final InetAddress ipv4Mask, final InetAddress ipv6Mask) {
    calc = new Masquerade(ipv4Mask, ipv6Mask);
  }

  /**
   * Masquerade any IP address with the bitmask set in the constructor of this object.
   *
   * @param IPAddress IP address to mask
   * @return A masqueraded IP address
   * @throws UnknownHostException If {@code IPAddress} is not a valid IP address
   */
  public InetAddress maskAny(final InetAddress IPAddress) throws UnknownHostException {
    InetAddress mask = Objects.requireNonNull(IPAddress);

    return calc.maskAnyAddress(mask);
  }

  /**
   * Masquerade only public routable IP addresses with the bitmask set in the constructor of this object.
   *
   * @param IPAddress IP address to mask
   * @return A masqueraded IP address
   * @throws UnknownHostException If {@code addressToMask} is not a valid IP address
   */
  public InetAddress maskPublicRoutableOnly(final InetAddress IPAddress)
      throws UnknownHostException {
    InetAddress mask = Objects.requireNonNull(IPAddress);

    return calc.maskPublicRoutableIPAddressOnly(mask);
  }


}
