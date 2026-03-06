package share

import "github.com/TypingHare/burrow/v2026/kernel"

type WebDecorationLike interface {
	kernel.DecorationInstance
	Spec() *WebSpec
}

type WebSpec struct {
	AutoRedirect     bool
	SilentlyRedirect bool
}

func ParseWebSpec(rawSpec kernel.RawSpec) (WebSpec, error) {
	autoRedirect, err := kernel.GetRawSpecValueOrDefault(
		rawSpec,
		"autoRedirect",
		true,
	)
	if err != nil {
		return WebSpec{}, err
	}

	silentlyRedirect, err := kernel.GetRawSpecValueOrDefault(
		rawSpec,
		"silentlyRedirect",
		true,
	)
	if err != nil {
		return WebSpec{}, err
	}

	return WebSpec{
		AutoRedirect:     autoRedirect,
		SilentlyRedirect: silentlyRedirect,
	}, nil
}
