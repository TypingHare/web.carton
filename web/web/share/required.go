package share

import "github.com/TypingHare/burrow/v2026/kernel"

type WebDecorationLike interface {
	kernel.DecorationInstance
	Spec() *WebSpec
}

type WebSpec struct {
	AutoDispatch     bool
	SilentlyDispatch bool
}

func ParseWebSpec(rawSpec kernel.RawSpec) (WebSpec, error) {
	return WebSpec{
		AutoDispatch:     true,
		SilentlyDispatch: true,
	}, nil
}
